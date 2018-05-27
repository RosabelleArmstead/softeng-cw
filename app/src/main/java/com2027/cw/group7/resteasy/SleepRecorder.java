package com2027.cw.group7.resteasy;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Timer was created with help from: https://en.proft.me/2017/11/18/how-create-count-timer-android/
 * Sounds from:  https://www.bensound.com/royalty-free-music/track/relaxing
 * Sound recorder created with help from: http://s2ptech.blogspot.gr/2014/01/how-to-detect-noise-in-android.html
 * MotionDetector created with help from: https://stackoverflow.com/questions/14574879/how-to-detect-movement-of-an-android-device
 */
public class SleepRecorder extends AppBaseActivity implements SensorEventListener {

    private static final int POLL_INTERVAL = 300; //Runnable will execute after this much time
    private static MediaPlayer mp; //Used for playing the soundscape
    private Button startSleep, stopSleep; //Buttons for starting and stoping the sleep recording
    private TextView timer; //Displays time elapsed after recording was started
    private TextView selectedSoundscape; //Displays selected soundscape
    private static String soundscapeSelected = "None";
    private long startTime, timeInMilliseconds = 0; //Used for calculating elapsed time
    private Handler customHandler = new Handler(); //For counting time on a different thread
    private boolean mRunning = false; //Flag that shows if the recorder is running
    private int mThreshold = 4; //Threshold for noise detection
    private PowerManager.WakeLock mWakeLock; //For keeping the device on while recording
    private Handler mHandler = new Handler(); //Handler for the runnable
    private SoundMeter mSensor; //Data source
    private int exceedSoundThreshold; //Used to count the amount of times that the sound amplitude exceeded the threshold set
    private long soundscapeStartTime, soundscapeTargetTime; //The start time of the soundscape and the end time, used to stop the soundscape after a certain amount of time
    private static boolean isSoundscape;
    private double sleepTime;

    //Used for motion detection
    private SensorManager sensorMan;
    private Sensor accelerometer;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private int exceedMovementThreshold;

    /**
     * Used to prevent user from recording sleep when battery is allready bellow 20%
     */
    private BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            if (level < 20 && stopSleep.getVisibility() == View.GONE) {
                Toast.makeText(getApplicationContext(), "Battery level is low please charge your device before recording.", Toast.LENGTH_SHORT).show();
                startSleep.setEnabled(false);
            } else {
                startSleep.setEnabled(true);
            }
        }
    };
    /**
     * Executed when battery is low, to notify the user and stop recording
     * (note: if battery is allready low when the recording starts it will not trigger,
     * that is why there is another receiver for the battery)
     */
    private BroadcastReceiver lowBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (stopSleep.getVisibility() == View.VISIBLE) {
                Toast.makeText(getApplicationContext(), "Battery level is low monitoring has stopped",
                        Toast.LENGTH_SHORT).show();
                stopSleep.performClick();
            }
        }
    };
    /**
     * Count time on a different thread
     */
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            timer.setText(getDateFromMillis(timeInMilliseconds));
            if (soundscapeTargetTime <= timeInMilliseconds && isSoundscape) {
                stopSoundscape();
            }

            customHandler.postDelayed(this, 1000);
        }
    };
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

    public static void setMp(Context context, String soundscape) {
        isSoundscape = true;
        soundscapeSelected = soundscape;
        if (soundscape.equals("Concentration")) {
            //set soundscape
            mp = MediaPlayer.create(context, R.raw.concentration);
        } else if (soundscape.equals("Fresh Air")) {
            //set soundscape
            mp = MediaPlayer.create(context, R.raw.freshair);
        } else if (soundscape.equals("Soaring")) {
            mp = MediaPlayer.create(context, R.raw.soaring);
        } else if (soundscape.equals("Sound")) {
            mp = MediaPlayer.create(context, R.raw.sound);
        } else if (soundscape.equals("None")) {
            isSoundscape = false;
        }
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

    @Override
    /**
     * Executed when the Activity is first run. Used to initialise variables and call methods.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_recorder);

        setupButtons(); //Initialise all the buttons
        timer = (TextView) findViewById(R.id.timer); //Initialise the textView that counts the time
        selectedSoundscape = (TextView) findViewById(R.id.soundscape_selected); //Initialise selected soundscape
        selectedSoundscape.setText("Soundscape Selected: " + soundscapeSelected);

        if (isSoundscape) {
            mp.setLooping(true); //Allow the audio clip to loop after it has finished
        }


        mSensor = new SoundMeter(); // Used to record voice

        //For keeping the device awake while the recorder is running and dimming the screen
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");

        exceedSoundThreshold = 0; // Number of times the sound threshold has been exceeded

        //Used for motion detection
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        exceedMovementThreshold = 0;

        this.registerReceiver(this.batteryLevelReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        this.registerReceiver(this.lowBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));

    }

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

                Log.d("SleepRecorder", "Starting mic...");
                start();

                if (isSoundscape) {
                    Log.d("SleepRecorder", "Starting soundscape...");
                    startSoundscape();
                }

            }
        });

        stopSleep = (Button) findViewById(R.id.stop_sleep);
        stopSleep.setVisibility(View.GONE);
        stopSleep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startSleep.setVisibility(View.VISIBLE);
                stopSleep.setVisibility(View.GONE);
                customHandler.removeCallbacks(updateTimerThread);

                //timer.getText().toString();
                Log.d("SleepRecorder", "Stopping mic...");
                stop();
                if (isSoundscape) {
                    Log.d("SleepRecorder", "Stopping soundscape...");
                    stopSoundscape();
                }



                Intent myIntent = new Intent(SleepRecorder.this, SleepReview.class);

                //Get time from timer and convert to minutes
                String time = timer.getText().toString();
                double minutes = 0;
                String[] split = time.split(":");

                try {
                    minutes += Double.parseDouble(split[0]) * 60;
                    minutes += Double.parseDouble(split[1]);
                    minutes += Math.round(Double.parseDouble(split[2]) / 60);
                } catch (Exception e) {
                    Log.e("minutes", "exception", e);
                }

                sleepTime = minutes / 60; // Convert minutes to hours
                String information = sleepTime + " " + exceedSoundThreshold + " " + exceedMovementThreshold;
                Log.d("SleepRecorder", information);

                myIntent.putExtra("information", information);
                SleepRecorder.this.startActivity(myIntent);
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
            Toast.makeText(getApplicationContext(), "Permissions accepted, please restart the recording",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Please keep your device unlocked while recording",
                    Toast.LENGTH_SHORT).show();
            mSensor.start(); //Start recording
        }

        Log.d("SleepRecorder", "Mic sensor started...");

        //Keep the screen on while recording
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }

        Log.d("SleepRecorder", "Screen lock acquired...");

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

    /**
     * It is executed when the sound threshold is exceeded, to increase the counter and start the soundscape
     */
    private void handleSound() {
        exceedSoundThreshold++;
        if(isSoundscape) {
            startSoundscape();
        }
        // Show alert when noise thersold crossed
        Toast.makeText(getApplicationContext(), "Sound Thersold Crossed " + exceedSoundThreshold,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Start playing the soundscape and update the start and target time.
     */
    private void startSoundscape() {
        mp.start();
        soundscapeStartTime = SystemClock.uptimeMillis() - startTime;
        soundscapeTargetTime = soundscapeStartTime + 10000 * 60; //Stop playing after 10 minutes
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
        if (isSoundscape) {
            stopSoundscape();
        }
        stop();
        unregisterReceiver(lowBatteryReceiver);
        unregisterReceiver(batteryLevelReceiver);
        sensorMan.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        this.registerReceiver(this.batteryLevelReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        this.registerReceiver(this.lowBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    /**
     * Executed when the Accelerometer sensor values change
     */
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            //Handle motion detection
            if (mAccel > 3) {
                exceedMovementThreshold++;
                if(isSoundscape) {
                    startSoundscape();
                }
                Toast.makeText(getApplicationContext(), "Motion Detected " + exceedMovementThreshold,
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
