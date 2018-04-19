package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    private TextView confirmPassword;
    private TextView editPassword;
    private TextView editEMail;
    private TextView editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        confirmPassword = findViewById(R.id.confirm_password);
        editPassword = findViewById(R.id.edit_password);
        editEMail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
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
        if (user == null || newEMail.isEmpty()) return null;

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
        if (user == null || newName.isEmpty()) return null;

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

                            if (taskList.isEmpty()) { return; }

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
    protected void onStart() {
        super.onStart();

        reevaluateAuthStatus();
    }
}
