package com2027.cw.group7.resteasy;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreatmentData extends FireStore {
    public String title;
    public String description;
    public HashMap<String, Object> otherVals;
    static public ArrayList<String> otherFields;

    static {
        otherFields = new ArrayList<String>();
        otherFields.add("6 to 3 years");
        otherFields.add("14 to 17 years");
        otherFields.add("18 to 64 years");
        otherFields.add("65+ years");
        otherFields.add("Male");
        otherFields.add("Female");
        otherFields.add("Other");
        otherFields.add("Less than 6 months");
        otherFields.add("6 months to 1 year");
        otherFields.add("1 to 2 years");
        otherFields.add("More than 2 years");
    }

    public TreatmentData() {
        otherVals = new HashMap<>();
    }

    public TreatmentData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public TreatmentData create() {
        return new TreatmentData();
    }

    Task<Void> save() {
        Log.d("RESTEASY_Treatment_Save", "TreatmentSuggestions");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.save(db.collection("TreatmentSuggestions").document(), this);
    }

    public static Task<Map<String, TreatmentData>> loadAll() {
        Log.d("RESTEASY_Treatment_LAll", "Treatments");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.loadAll(db.collection("Treatments"),
                new TreatmentData());
    }
}
