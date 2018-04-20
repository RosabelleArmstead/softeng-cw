package com2027.cw.group7.resteasy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppBaseActivity {
    private Button logoutButton;
    private Button loginButton;
    private TextView loggedInText;
    private TextView loggedName;
    private TextView loggedOutText;

    public void loginClick(View v) {
        login();
    }

    public void logoutClick(View v) {
        logout();
    }

    @Override
    protected void reevaluateAuthStatus() {
        super.reevaluateAuthStatus();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            logoutButton.setVisibility(Button.VISIBLE);
            loginButton.setVisibility(Button.GONE);
            loggedInText.setVisibility(View.VISIBLE);
            loggedName.setText(user.getDisplayName());
            loggedName.setVisibility(View.VISIBLE);
            loggedOutText.setVisibility(View.GONE);
        } else {
            logoutButton.setVisibility(Button.GONE);
            loginButton.setVisibility(Button.VISIBLE);
            loggedOutText.setVisibility(View.VISIBLE);
            loggedName.setText("");
            loggedName.setVisibility(View.INVISIBLE);
            loggedInText.setVisibility(View.GONE);
            loggedName.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton = findViewById(R.id.logoutButton);
        loginButton = findViewById(R.id.loginButton);
        loggedInText = findViewById(R.id.loggedInText);
        loggedName = findViewById(R.id.loggedName);
        loggedOutText = findViewById(R.id.loggedOutText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        reevaluateAuthStatus();
    }

}