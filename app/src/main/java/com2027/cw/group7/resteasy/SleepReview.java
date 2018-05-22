package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SleepReview extends AppBaseActivity {

    private List<String> titles = new ArrayList<String>();


    private String information; // Sleep recording information
    private int sleepTime; // Time of sleep
    private int exceedSoundThreshold; // How many times the sound threshold was exceeded
    private int exceedMovementThreshold; // How many times the sound threshold was exceeded

    private TextView sleepRating; // The user's rating out of 5 (compulsory)
    private EditText comment; // The user's comment
    private RatingBar ratingBar;
    private Spinner treatmentsSpinner; // Allows user to select a treatment, if any were used
    private String selectedTreatment; // Holds selected treatment
    private Button submit; // Submit sleep review
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_review);
        getIntentInformation();

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        comment = (EditText) findViewById(R.id.add_comment);
        treatmentsSpinner = (Spinner) findViewById(R.id.treatments_spinner);
        sleepRating = (TextView) findViewById(R.id.sleep_rating);
        submit = (Button) findViewById(R.id.submit_sleep_review);

        // Execute when the user changes his rating, to update the sleep score
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser) {
                if (ratingBar.getRating() < 1) ratingBar.setRating(1);
                sleepRating.setText("Sleep Rating: " + calculateSleepRating(sleepTime, exceedSoundThreshold, exceedMovementThreshold));
            }
        });

        selectedTreatment = "None"; // To indicate that no treatment is used

        loadTreatments(); // Load treatments from server to spinner

        setupSubmitButton(); // Setup button for submitting sleep review

    }

    /**
     * Simple method that sets up the submit buton. Its function is to retrieve all the user
     * data for this night's sleep and save it on the database. Lastly it redirects
     * the user back to the SleepRecorder page.
     */
    private void setupSubmitButton() {

        submit.setEnabled(false); // Disable until user fills out necessary information

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get current user, so that his data can be saved
                Date d = Calendar.getInstance().getTime(); //Get current date

                int sleepScore = 0; // Set initial sleep score

                try {
                    //Get sleep score from textView
                    sleepScore = Integer.parseInt(sleepRating.getText().toString().substring(sleepRating.getText().toString().lastIndexOf(" ") + 1));
                } catch (Exception e) {
                }

                //Get current date
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //Set date format
                String date = df.format(d);

                // Creat object for sleep data with values from user
                SleepData sd = new SleepData(Math.round(ratingBar.getRating()), sleepScore, sleepTime, selectedTreatment, comment.getText().toString(), date);

                // Save sleep data for user
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

            }
        });
    }

    /**
     * Retrieve all treatments from database and setup the spinner using those
     */
    private void loadTreatments() {

        //Receive all treatment titles
        TreatmentData.loadAll().addOnCompleteListener(
                new OnCompleteListener<Map<String, TreatmentData>>() {
                    @Override
                    public void onComplete(@NonNull Task<Map<String, TreatmentData>> t) {
                        Map<String, TreatmentData> map = t.getResult();
                        titles.add("None"); //Used as default value for spinner, user has not used a treatment
                        for (Map.Entry<String, TreatmentData> entry : map.entrySet()) {
                            TreatmentData td = entry.getValue();
                            titles.add(td.title);

                        }
                        // Create adapter that loads treatment titles in spinner
                        ArrayAdapter<String> infoSpinnerAdapter = new ArrayAdapter<String>(SleepReview.this, android.R.layout.simple_spinner_item, titles);
                        infoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        treatmentsSpinner.setAdapter(infoSpinnerAdapter);

                        // When a treatment is selected save its title
                        treatmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedTreatment = adapterView.getItemAtPosition(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                //Auto-generated
                            }
                        });

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
        submit.setEnabled(true);

        return Integer.toString(intSleepTime);
    }

    @Override
    /**
     * Ensure user is authenticated
     */
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

        reevaluateAuthStatus(); // Check if user is authenticated when the application starts
    }
}
