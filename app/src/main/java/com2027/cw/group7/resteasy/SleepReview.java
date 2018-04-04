package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class SleepReview extends AppBaseActivity {

    private String information;
    private int sleepTime;
    private int exceedSoundThreshold;
    private int exceedMovementThreshold;

    private TextView sleepRating;
    private RatingBar ratingBar;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_review);
        getIntentInformation();

        sleepRating = (TextView) findViewById(R.id.sleep_rating);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar,
                                                float rating, boolean fromUser) {
                        if (ratingBar.getRating() < 1) ratingBar.setRating(1);
                        sleepRating.setText("Sleep Rating: " + calculateSleepRating(sleepTime, exceedSoundThreshold, exceedMovementThreshold));
                    }
                });

        submit = (Button) findViewById(R.id.submit_sleep_review);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(SleepReview.this, SleepRecorder.class);
                SleepReview.this.startActivity(myIntent);
            }
        });
    }

    /**
     * Method used to receive information from SleepRecorder Activity, so sleep quality can be quantified
     */
    private void getIntentInformation() {
        Intent intent = getIntent();
        information = intent.getStringExtra("information");
        Log.d("SleepReview Information",information);

        String[] splited = information.split("\\s+");

        try {
            sleepTime = Integer.parseInt(splited[0]);
            exceedSoundThreshold = Integer.parseInt(splited[1]);
            exceedMovementThreshold = Integer.parseInt(splited[2]);
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
    }

    /**
     * Calculate sleep quality rating using sleep data
     *
     * @param sleepTime time of sleep in hours
     * @param exceedSoundThreshold # of times the sound threshold was crossed
     * @param exceedMovementThreshold # of times the movement threshold was crossed
     * @return the sleep rating
     */
    private String calculateSleepRating(int sleepTime, int exceedSoundThreshold, int exceedMovementThreshold) {
        double sleepRating = 60;
        Log.d("SleepReview Ints", sleepTime +" "+ exceedSoundThreshold+" "+ exceedMovementThreshold);

        sleepRating = (sleepRating - (exceedSoundThreshold + exceedMovementThreshold) - (Math.abs(sleepTime - 7.5)) * 4) * (ratingBar.getRating()/3);
        if (sleepRating<0) sleepRating = 0;
        if (sleepRating>100) sleepRating = 100;
        int intSleepTime = (int) Math.round(sleepRating);
        //return Integer.toString(Math.round(sleepTime));
        return Integer.toString(intSleepTime);
    }

}
