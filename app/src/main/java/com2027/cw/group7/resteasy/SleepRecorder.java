package com2027.cw.group7.resteasy;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Timer was created with help from: https://en.proft.me/2017/11/18/how-create-count-timer-android/
 *
 *
 * sounds from:  https://www.bensound.com/royalty-free-music/track/relaxing
 */
public class SleepRecorder extends AppBaseActivity {

    private Button startSleep, stopSleep; //Buttons for starting and stoping the sleep recording
    private TextView timer; //Displays time elapsed after recording was started
    private long startTime, timeInMilliseconds = 0; //Used for calculating elapsed time
    private Handler customHandler = new Handler(); //For counting time on a different thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_recorder);

        setupButtons();//Initialise all the buttons
        timer = (TextView) findViewById(R.id.timer);//Initialise the textView that counts the time



    }

    /**
     * Converts miliseconds to Hours::Minutes::Seconds
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
                Toast.makeText(getApplicationContext(), timer.getText().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
