package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

public class Treatments extends AppBaseActivity {

    private Button suggestionBtn;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    private ArrayList<Boolean> suggestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);

        initTreatments();

        suggestionBtn = (Button)findViewById(R.id.suggest_button);
        suggestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTreatmentSuggestion();

            }
        });
    }

    //Method to open the suggestion Activity from the Treatments activity
    public void openTreatmentSuggestion(){

        Intent myIntent = new Intent(this, TreatmentSuggestion.class);
        startActivity(myIntent);

    }


    private void initTreatments(){
/*
        titles.add("Room Temperature");
        descriptions.add("Keep your room temperature as close to 18.5 degrees celsius as possible.");

        titles.add("Sleep Mask");
        descriptions.add("Using a sleep mask helps your body produce Melatonin which aids falling asleep");

        titles.add("Ear Plugs");
        descriptions.add("Using ear plugs reduces disturbances from noise. When blocking out both noise and light your chances of falling asleep are improved.");

        titles.add("Pillow Spray");
        descriptions.add("Using a pillow spray with certain scents such as Lavender, can promote the release of sleep hormones");
*/
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        UserData.load(user.getUid()).addOnCompleteListener(new OnCompleteListener<UserData>() {
            @Override
            public void onComplete(@NonNull Task<UserData> t) {
                final UserData userData = t.getResult();
                TreatmentData.loadAll().addOnCompleteListener(
                        new OnCompleteListener<Map<String, TreatmentData>>() {
                            @Override
                            public void onComplete(@NonNull Task<Map<String, TreatmentData>> t) {
                                Map<String, TreatmentData> map = t.getResult();
                                for (Map.Entry<String, TreatmentData> entry : map.entrySet()) {
                                    TreatmentData td = entry.getValue();
                                    titles.add(td.title);
                                    descriptions.add(td.description);
                                    Boolean suggestion = null;
                                    if (td.otherVals.containsKey(userData.ageRange) &&
                                        td.otherVals.containsKey(userData.sex) &&
                                        td.otherVals.containsKey(userData.suffering)) {
                                        long average = ((long)td.otherVals.get(userData.ageRange) +
                                                        (long)td.otherVals.get(userData.sex) +
                                                        (long)td.otherVals.get(userData.suffering)) / 3;

                                        if (average >= 60) {
                                            suggestion = true;
                                        } else if (average <= 40) {
                                            suggestion = false;
                                        }

                                    } else {
                                        Log.d("RESTEASY_Treatment", "Problem User: " + userData.toString());
                                        Log.d("RESTEASY_Treatment", "Problem Treatment: " + td.otherVals.toString());
                                    }
                                    suggestions.add(suggestion);
                                }
                                initRecyclerView();
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            }
                        });
            }});
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.treatment_recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titles, descriptions, suggestions,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
