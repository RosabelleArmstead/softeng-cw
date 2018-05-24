package com2027.cw.group7.resteasy;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserData extends FireStore {
    public String defaultSoundscape;
    public String ageRange;
    public String sex;
    public String suffering;

    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserData create() {
        return new UserData();
    }

    public UserData(String defaultSoundscape) {
        this.defaultSoundscape = defaultSoundscape;
    }

    Task<Void> save(String uid) {
        Log.d("RESTEASY_UserData_Save", "UserData: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.save(db.collection("UserData").document(uid), this);
    }

    static Task<UserData> load(String uid) {
        Log.d("RESTEASY_UserData_Save", "UserData: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FireStore.load(db.collection("UserData").document(uid), new UserData());
    }
}
