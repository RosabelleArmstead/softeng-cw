package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SleepReview extends AppBaseActivity {

    private String information;
    private int sleepTime;
    private int exceedSoundThreshold;
    private int exceedMovementThreshold;

    private TextView sleepRating;
    private EditText comment;
    private RatingBar ratingBar;
    private Button submit;
    //private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_review);
        getIntentInformation();

        //myDb = new DatabaseHelper(this);

        sleepRating = (TextView) findViewById(R.id.sleep_rating);
        comment = (EditText) findViewById(R.id.add_comment);

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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Date d = Calendar.getInstance().getTime(); //Get current date
                /*
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); //Set date format
                String date = df.format(d);
                */
                String treatment = "test-none";
                int sleepScore = 0;
                try {
                    sleepScore = Integer.parseInt(sleepRating.getText().toString().substring(sleepRating.getText().toString().lastIndexOf(" ") + 1));
                } catch (Exception e) {
                }
                //boolean isInserted = myDb.insertData(date, Math.round(ratingBar.getRating()), sleepScore, sleepTime, treatment, comment.getText().toString());

                SleepData sd = new SleepData(Math.round(ratingBar.getRating()), sleepScore, sleepTime, treatment, comment.getText().toString());
                sd.save(user.getUid(), d).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> t) {
                        if (t.isSuccessful()) {
                            Toast.makeText(SleepReview.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SleepReview.this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
                        }

                        Intent myIntent = new Intent(SleepReview.this, SleepRecorder.class);
                        SleepReview.this.startActivity(myIntent);
                    }
                });

                //if (isInserted) Toast.makeText(SleepReview.this,"Data Inserted", Toast.LENGTH_SHORT).show();
                //else Toast.makeText(SleepReview.this,"Data Insertion Failed", Toast.LENGTH_SHORT).show();

                //Intent myIntent = new Intent(SleepReview.this, SleepRecorder.class);
                //SleepReview.this.startActivity(myIntent);
            }
        });
    }

    /**
     * Method used to receive information from SleepRecorder Activity, so sleep quality can be quantified
     */
    private void getIntentInformation() {
        Intent intent = getIntent();
        information = intent.getStringExtra("information");
        Log.d("SleepReview Information", information);

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
     * @param sleepTime               time of sleep in hours
     * @param exceedSoundThreshold    # of times the sound threshold was crossed
     * @param exceedMovementThreshold # of times the movement threshold was crossed
     * @return the sleep rating
     */
    private String calculateSleepRating(int sleepTime, int exceedSoundThreshold, int exceedMovementThreshold) {
        double sleepRating = 60;
        Log.d("SleepReview Ints", sleepTime + " " + exceedSoundThreshold + " " + exceedMovementThreshold);

        sleepRating = (sleepRating - (exceedSoundThreshold + exceedMovementThreshold) - (Math.abs(sleepTime - 7.5)) * 4) * (ratingBar.getRating() / 3);
        if (sleepRating < 0) sleepRating = 0;
        if (sleepRating > 100) sleepRating = 100;
        int intSleepTime = (int) Math.round(sleepRating);
        //return Integer.toString(Math.round(sleepTime));
        return Integer.toString(intSleepTime);
    }

    @Override
    protected void reevaluateAuthStatus() {
        super.reevaluateAuthStatus();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // The user logged out, so send him back to the Home screen
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        reevaluateAuthStatus();
    }
}
