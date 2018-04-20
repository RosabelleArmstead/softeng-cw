package com2027.cw.group7.resteasy;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserData {
    public String defaultSoundscape;

    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserData(String defaultSoundscape) {
        this.defaultSoundscape = defaultSoundscape;
    }
}
