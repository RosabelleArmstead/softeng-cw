package com2027.cw.group7.resteasy;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class TreatmentData extends FireStore {
    public String title;
    public String description;

    public TreatmentData() {

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
        return FireStore.loadAll(db.collection("Treatments"), new TreatmentData());
    }
}
