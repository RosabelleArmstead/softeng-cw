package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Treatments extends AppBaseActivity {

    private Button suggestionBtn;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
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

        titles.add("Room Temperature");
        descriptions.add("Keep your room temperature as close to 18.5 degrees celsius as possible.");

        titles.add("Sleep Mask");
        descriptions.add("Using a sleep mask helps your body produce Melatonin which aids falling asleep");

        titles.add("Ear Plugs");
        descriptions.add("Using ear plugs reduces disturbances from noise. When blocking out both noise and light your chances of falling asleep are improved.");

        titles.add("Pillow Spray");
        descriptions.add("Using a pillow spray with certain scents such as Lavender, can promote the release of sleep hormones");

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.treatment_recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titles, descriptions, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
