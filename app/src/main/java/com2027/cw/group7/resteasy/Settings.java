package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppBaseActivity {
    private EditText confirmPassword;
    private EditText editPassword;
    private EditText editEMail;
    private EditText editName;
    private Spinner editAge;
    private Spinner editSex;
    private Spinner editSuffering;
    private Spinner soundscapeSpinner;
    private ArrayAdapter<CharSequence> infoSpinnerAdapter;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        userData = new UserData();

        confirmPassword = findViewById(R.id.confirm_password);
        editPassword = findViewById(R.id.edit_password);
        editEMail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editAge = findViewById(R.id.age);
        editSex = findViewById(R.id.sex);
        editSuffering = findViewById(R.id.suffering);

        // Load default value
        if (user != null) {
            editEMail.setText(user.getEmail());
            editName.setText(user.getDisplayName());

            UserData.load(user.getUid()).addOnCompleteListener(new OnCompleteListener<UserData>() {
                @Override
                public void onComplete(@NonNull Task<UserData> t) {
                    userData = t.getResult();
                    addSoundscapeToSpinner();
                    addListenerToSpinner();
                    if (userData.defaultSoundscape != null) {
                        Log.d("RESTEASY_UserData", "Default Soundscape: " + userData.defaultSoundscape);
                        int spinnerPosition = infoSpinnerAdapter.getPosition(userData.defaultSoundscape);
                        soundscapeSpinner.setSelection(spinnerPosition);
                    }
                    Log.d("RESTEASY_UserData",
                            userData.ageRange + " " +
                                    userData.sex + " " + userData.suffering);
                    for (int i=0; i < editAge.getCount(); i++){
                        if (editAge.getItemAtPosition(i).toString().equals(userData.ageRange)){
                            editAge.setSelection(i);
                            break;
                        }
                    }
                    for (int i=0; i < editSex.getCount(); i++){
                        if (editSex.getItemAtPosition(i).toString().equals(userData.sex)){
                            editSex.setSelection(i);
                            break;
                        }
                    }
                    for (int i=0; i < editSuffering.getCount(); i++){
                        if (editSuffering.getItemAtPosition(i).toString().equals(userData.suffering)){
                            editSuffering.setSelection(i);
                            break;
                        }
                    }
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
            });
        }
    }

    private void addSoundscapeToSpinner() {
        soundscapeSpinner = findViewById(R.id.soundscape_spinner);

        infoSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.soundscapes, android.R.layout.simple_spinner_item);
        infoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundscapeSpinner.setAdapter(infoSpinnerAdapter);
    }

    private void  addListenerToSpinner() {
        soundscapeSpinner = findViewById(R.id.soundscape_spinner);

        soundscapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedItem = adapterView.getItemAtPosition(i).toString();
                    SleepRecorder.setMp(getApplicationContext(), selectedItem);
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //notify user to select soundscape
            }
        });
    }

    private Task<Void> updateUserData(String soundscape, String age, String sex, String suffering) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || soundscape.isEmpty() ||
                (soundscape.equals(userData.defaultSoundscape) &&
                age.equals(userData.ageRange) &&
                sex.equals(userData.sex) &&
                suffering.equals(userData.suffering))) return null;

        userData.defaultSoundscape = soundscape;
        userData.ageRange = age;
        userData.sex = sex;
        userData.suffering = suffering;
        return userData.save(user.getUid());
    }

    private Task<Void> updatePassword(String newPass) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || newPass.isEmpty()) return null;

        return user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("RESTEASY", "Password updated");
                    editPassword.setText("");
                } else {
                    alertDialog("Error", "Error password not updated");
                    Log.d("RESTEASY", "Error password not updated");
                }
            }
        });
    }

    private Task<Void> updateEMail(String newEMail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || newEMail.isEmpty() || newEMail.equals(user.getEmail())) return null;

        return user.updateEmail(newEMail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("RESTEASY", "EMail updated");
                    editEMail.setText("");
                } else {
                    alertDialog("Error", "Error EMail not updated");
                    Log.d("RESTEASY", "Error EMail not updated");
                }
            }
        });
    }

    private Task<Void> updateProfile(String newName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || newName.isEmpty() || newName.equals(user.getDisplayName())) return null;

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        return user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("RESTEASY", "Profile updated");
                    editName.setText("");
                } else {
                    alertDialog("Error", "Error EMail not updated");
                    Log.d("RESTEASY", "Error profile not updated");
                }
            }
        });
    }

    public void saveSettings(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        String currentPassword = confirmPassword.getText().toString();

        if (currentPassword.isEmpty()) {
            alertDialog("Error", "You need to supply your current password to change settings");
            return;
        }

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), currentPassword);

        String pass = editPassword.getText().toString();
        if (pass.length() > 0) {
            if (pass.length() < 8) {
                Toast.makeText(Settings.this, "Password must be more than 8 characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pass.matches(".*\\d+.*")) {
                Toast.makeText(Settings.this, "Password must contain a number.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pass.matches(".*[A-Z]+.*")) {
                Toast.makeText(Settings.this, "Password must contain a capital letter.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            List<Task<Void>> taskList = new ArrayList<Task<Void>>();

                            Task<Void> passwordTask = updatePassword(editPassword.getText().toString());
                            if (passwordTask != null) taskList.add(passwordTask);

                            Task<Void> emailTask = updateEMail(editEMail.getText().toString());
                            if (emailTask != null) taskList.add(emailTask);

                            Task<Void> profileTask = updateProfile(editName.getText().toString());
                            if (profileTask != null) taskList.add(profileTask);

                            Task<Void> updateUserData = updateUserData(
                                    soundscapeSpinner.getSelectedItem().toString(),
                                    editAge.getSelectedItem().toString(),
                                    editSex.getSelectedItem().toString(),
                                    editSuffering.getSelectedItem().toString());
                            if (updateUserData != null) taskList.add(updateUserData);

                            if (taskList.isEmpty()) {
                                alertDialog("Warning", "No settings were changed");
                                confirmPassword.setText("");
                                return;
                            }

                            Tasks.whenAllComplete(taskList).
                                addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                                    @Override
                                    public void onComplete(@NonNull Task<List<Task<?>>> task) {
                                        List<Task<?>> tasks = task.getResult();
                                        boolean success = true;
                                        for (Task<?> t : tasks) {
                                            success = success && t.isSuccessful();
                                        }
                                        if (success) {
                                            alertDialog("Success", "All settings were applied");
                                            confirmPassword.setText("");
                                        } else {
                                            alertDialog("Error", "Some settings failed to apply");
                                        }
                                    }
                                }
                            );
                        } else {
                            Log.d("RESTEASY", "Error re-auth failed");
                            alertDialog("Error", "Your current password is incorrect or other authentication error");
                        }
                    }
                });
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
