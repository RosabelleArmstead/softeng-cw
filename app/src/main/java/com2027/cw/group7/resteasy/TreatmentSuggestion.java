package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TreatmentSuggestion extends AppBaseActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_suggestion);

        editTextTitle = findViewById(R.id.user_suggestion_title);
        editTextDescription = findViewById(R.id.user_suggestion_description);
        submitBtn = findViewById(R.id.submit_suggestion);

        editTextTitle.addTextChangedListener(suggestionTextWatcher);
        editTextDescription.addTextChangedListener(suggestionTextWatcher);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreatmentData td = new TreatmentData(editTextTitle.getText().toString(),
                        editTextDescription.getText().toString());
                td.save().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> t) {
                        Toast.makeText(TreatmentSuggestion.this, "Suggestion Submitted!", Toast.LENGTH_LONG).show();
                        backToTreatments();
                    }
                });
            }
        });
    }


    //Method to enable button only when both editText have been filled.
    private TextWatcher suggestionTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String userTitle = editTextTitle.getText().toString();
            String userDescription = editTextDescription.getText().toString();

            submitBtn.setEnabled(!userTitle.isEmpty() && !userDescription.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void backToTreatments(){

        Intent myIntent = new Intent(this, Treatments.class);
        startActivity(myIntent);
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
