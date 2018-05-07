package com2027.cw.group7.resteasy;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SleepData extends FireStore {
    public int userRating;
    public int sleepRating;
    public float userSleepTime;
    public String treatment;
    public String comment;

    private SleepData() {

    }

    public SleepData create() {
        return new SleepData();
    }

    public SleepData(int userRating, int sleepRating,
                      float userSleepTime, String treatment, String comment) {
        this.userRating = userRating;
        this.sleepRating = sleepRating;
        this.userSleepTime = userSleepTime;
        this.treatment = treatment;
        this.comment = comment;
    }

    public Task<Void> save(String uid, Date d) {
        Log.d("RESTEASY_SleepData_Save", "SleepData: " + uid);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //Set date format
        String date = df.format(d);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.save(db.collection("SleepData").document(uid)
                .collection("SleepDays").document(date), this);
    }

    public static Task<SleepData> load(String uid, Date d) {
        Log.d("RESTEASY_SleepData_Load", "SleepData: " + uid);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //Set date format
        String date = df.format(d);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.load(db.collection("SleepData").document(uid)
                .collection("SleepDays").document(date), new SleepData());
    }

    public static Task<Map<String, SleepData>> loadAll(String uid) {
        Log.d("RESTEASY_SleepData_LAll", "SleepData: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.loadAll(db.collection("SleepData").
                document(uid).collection("SleepDays"), new SleepData());
    }
}
