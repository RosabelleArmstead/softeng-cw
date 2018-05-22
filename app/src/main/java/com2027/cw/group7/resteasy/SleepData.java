package com2027.cw.group7.resteasy;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Map;

public class SleepData extends FireStore {
    public long userRating;
    public long sleepRating;
    public double userSleepTime;
    public String treatment;
    public String comment;
    public String date;
    private SleepData() {

    }

    public SleepData create() {
        return new SleepData();
    }

    public SleepData(long userRating, long sleepRating,
                      float userSleepTime, String treatment, String comment, String date) {
        this.userRating = userRating;
        this.sleepRating = sleepRating;
        this.userSleepTime = userSleepTime;
        this.treatment = treatment;
        this.comment = comment;
        this.date = date;
    }

    public Task<Void> save(String uid, Date d) {
        Log.d("RESTEASY_SleepData_Save", "SleepData: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.save(db.collection("SleepData").document(uid)
                .collection("SleepDays").document(), this);
    }

    public static Task<SleepData> load(String uid, Date d) {
        Log.d("RESTEASY_SleepData_Load", "SleepData: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.load(db.collection("SleepData").document(uid)
                .collection("SleepDays").document(), new SleepData());
    }

    public static Task<Map<String, SleepData>> loadAll(String uid) {
        Log.d("RESTEASY_SleepData_LAll", "SleepData: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.loadAll(db.collection("SleepData").
                document(uid).collection("SleepDays"), new SleepData());
    }
}
