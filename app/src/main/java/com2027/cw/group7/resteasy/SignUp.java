package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppBaseActivity {
    EditText displayName;
    EditText username;
    EditText password;
    EditText confirmPassword;
    Spinner age;
    Spinner sex;
    Spinner suffering;
    CheckBox termsCheck;
    TextView termsButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        displayName = (EditText)findViewById(R.id.displayname);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirm_password);
        age = (Spinner)findViewById(R.id.age);
        sex = (Spinner)findViewById(R.id.sex);
        suffering = (Spinner)findViewById(R.id.suffering);
        termsCheck = (CheckBox)findViewById(R.id.termsCheck);
        termsButton = (TextView)findViewById(R.id.termsButton);
        termsButton.setText(Html.fromHtml("<a href='https://rest-easy-70269.firebaseapp.com/tos.html'>Terms and Conditions</a>"));
        termsButton.setMovementMethod(LinkMovementMethod.getInstance());
        termsButton.setClickable(true);
        mAuth = FirebaseAuth.getInstance();
    }


    public void loginBack(View v) {
        startActivity(new Intent(this, Login.class));
        finish();
    }

    public void signUpSubmit(View v) {
        if (displayName.getText().length() == 0) {
            Toast.makeText(SignUp.this, "Empty username field.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.getText().length() == 0) {
            Toast.makeText(SignUp.this, "Empty email field.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().length() == 0) {
            Toast.makeText(SignUp.this, "Empty password field.", Toast.LENGTH_SHORT).show();
            return;
        }
        String pass = password.getText().toString();
        if (!pass.equals(confirmPassword.getText().toString())) {
            Toast.makeText(SignUp.this, "Passwords do not match.",  Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 8) {
            Toast.makeText(SignUp.this, "Password must be more than 8 characters.",  Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.matches(".*\\d+.*")) {
            Toast.makeText(SignUp.this, "Password must contain a number.",  Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.matches(".*[A-Z]+.*")) {
            Toast.makeText(SignUp.this, "Password must contain a capital letter.",  Toast.LENGTH_SHORT).show();
            return;
        }
        if (!termsCheck.isChecked()) {
            Toast.makeText(SignUp.this, "Terms and Conditions not accepted.",  Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("RESTEASY_Sign", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null) {
                                return;
                            }
                            List<Task<Void>> taskList = new ArrayList<Task<Void>>();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName.getText().toString())
                                    .build();

                            taskList.add(user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("RETEASY_Sign", "User profile updated.");
                                            }
                                            else {
                                                Log.w("RESTEASY_Sign", "createUserProfile:failure", task.getException());
                                                Toast.makeText(SignUp.this, "Profile update failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }));

                            UserData userData = new UserData();
                            userData.ageRange = age.getSelectedItem().toString();
                            userData.sex = sex.getSelectedItem().toString();
                            userData.suffering = suffering.getSelectedItem().toString();
                            taskList.add(userData.save(user.getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("RETEASY_Sign", "User data updated.");
                                    }
                                    else {
                                        Log.w("RESTEASY_Sign", "createUserData:failure", task.getException());
                                        Toast.makeText(SignUp.this, "User data update failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }));

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
                                              alertDialog("Success", "Sign up completed successfully");
                                          } else {
                                              alertDialog("Error", "Sign up did not complete successfully");
                                          }
                                          finish();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("RESTEASY_Sign", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
