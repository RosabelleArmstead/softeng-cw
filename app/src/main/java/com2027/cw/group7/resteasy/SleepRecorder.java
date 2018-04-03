package com2027.cw.group7.resteasy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Timer was created with help from: https://en.proft.me/2017/11/18/how-create-count-timer-android/
 * Sounds from:  https://www.bensound.com/royalty-free-music/track/relaxing
 * Sound recorder created with help from: http://s2ptech.blogspot.gr/2014/01/how-to-detect-noise-in-android.html
 * MotionDetector created with help from: https://stackoverflow.com/questions/14574879/how-to-detect-movement-of-an-android-device
 */
public class SleepRecorder extends AppBaseActivity implements SensorEventListener {

    private Button startSleep, stopSleep; //Buttons for starting and stoping the sleep recording
    private TextView timer; //Displays time elapsed after recording was started
    private long startTime, timeInMilliseconds = 0; //Used for calculating elapsed time
    private Handler customHandler = new Handler(); //For counting time on a different thread

    private static final int POLL_INTERVAL = 300; //Runnable will execute after this much time
    private boolean mRunning = false; //Flag that shows if the recorder is running
    private int mThreshold = 4; //Threshold for noise detection
    private PowerManager.WakeLock mWakeLock; //For keeping the device on while recording
    private Handler mHandler = new Handler(); //Handler for the runnable
    private SoundMeter mSensor; //Data source

    private int exceedSoundThreshold; //Used to count the amount of times that the sound amplitude exceeded the threshold set

    private MediaPlayer mp; //Used for playing the soundscape

    private long soundscapeStartTime, soundscapeTargetTime; //The start time of the soundscape and the end time, used to stop the soundscape after a certain amount of time

    //Used for motion detection
    private SensorManager sensorMan;
    private Sensor accelerometer;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private int exceedMovementThreshold;

    @Override
    /**
     * Executed when the Activity is first run. Used to initialise variables and call methods.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_recorder);

        setupButtons(); //Initialise all the buttons
        timer = (TextView) findViewById(R.id.timer); //Initialise the textView that counts the time

        mp = MediaPlayer.create(getApplicationContext(), R.raw.sound); //Initialise the mediaplayer with the specified audio file
        mp.setLooping(true); //Allow the audio clip to loop after it has finished


        mSensor = new SoundMeter(); // Used to record voice

        //For keeping the device awake while the recorder is running
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");

        exceedSoundThreshold = 0; // Number of times the sound threshold has been exceeded

        //Used for motion detection
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        exceedMovementThreshold = 0;
    }

    /**
     * Converts miliseconds to Hours::Minutes::Seconds
     *
     * @param d input time in miliseconds
     * @return formated time
     */
    public static String getDateFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    /**
     * Count time on a different thread
     */
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            timer.setText(getDateFromMillis(timeInMilliseconds));
            if (soundscapeTargetTime <= timeInMilliseconds) {
                stopSoundscape();
            }

            customHandler.postDelayed(this, 1000);
        }
    };

    /**
     * Create all the buttons for the activity with their listeners
     */
    private void setupButtons() {
        startSleep = (Button) findViewById(R.id.start_sleep);
        startSleep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startSleep.setVisibility(View.GONE);
                stopSleep.setVisibility(View.VISIBLE);
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                mRunning = true;
                start();
                startSoundscape();

            }
        });

        stopSleep = (Button) findViewById(R.id.stop_sleep);
        stopSleep.setVisibility(View.GONE);
        stopSleep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startSleep.setVisibility(View.VISIBLE);
                stopSleep.setVisibility(View.GONE);
                customHandler.removeCallbacks(updateTimerThread);
                timer.getText().toString();
                stop();
                stopSoundscape();
            }
        });
    }

    /**
     * Start the sound recording
     */
    private void start() {
        //Check if the App has permission to record audio and if not, as for permission.
        if (ActivityCompat.checkSelfPermission(SleepRecorder.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SleepRecorder.this, new String[]{Manifest.permission.RECORD_AUDIO},
                    1);

        } else {
            mSensor.start(); //Start recording
        }

        //Keep the screen on while recording
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }

        // Register sensor
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    /**
     * Stop the sound monitoring
     */
    private void stop() {

        //Stop keeping the screen on, since the monitoring has stopped
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }

        mHandler.removeCallbacks(mPollTask); //Remove pending posts of the runnable
        mSensor.stop(); //Stop recording audio
        mRunning = false; //Set the audio monitoring flag to false
        sensorMan.unregisterListener(this);
    }

    // Create runnable thread to monitor sound
    private Runnable mPollTask = new Runnable() {
        public void run() {

            double amp = mSensor.getAmplitude(); //Get sound amplitude from the microphone

            //If the amplitude recorded is higher than the threshold, call method that handles this event
            if ((amp > mThreshold)) {
                handleSound();
            }

            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);

        }
    };

    /**
     * It is executed when the sound threshold is exceeded, to increase the counter and start the soundscape
     */
    private void handleSound() {
        exceedSoundThreshold++;
        startSoundscape();
        // Show alert when noise thersold crossed
        Toast.makeText(getApplicationContext(), "Sound Thersold Crossed "+ exceedSoundThreshold,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Start playing the soundscape and update the start and target time.
     */
    private void startSoundscape() {
        mp.start();
        soundscapeStartTime = SystemClock.uptimeMillis() - startTime;
        soundscapeTargetTime = soundscapeStartTime + 10000; //Stop playing after 10 seconds
    }

    /**
     * Stop playing the soundscape by pausing it and reseting the time to 0
     * (tried using mp.stop(), but it didn't work)
     */
    private void stopSoundscape() {
        mp.pause();
        mp.seekTo(0);
    }
    
    /**
     * To stop sound from playing and monitoring when activity is no longer in the foreground,
     * as well as unregistering the Accelerometer listener
     */
    protected void onPause() {
        super.onPause();
        stopSoundscape();
        stop();
        sensorMan.unregisterListener(this);
    }

    /**
     * Executed when the Accelerometer sensor values change
     */
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            //Handle motion detection
            if(mAccel > 3){
                exceedMovementThreshold++;
                startSoundscape();
                Toast.makeText(getApplicationContext(), "Motion Detected "+ exceedMovementThreshold,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * This method is required by the Sensor event listener interface
     */
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }


}
