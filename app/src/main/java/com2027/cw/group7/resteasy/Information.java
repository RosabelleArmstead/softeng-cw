package com2027.cw.group7.resteasy;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Information extends AppBaseActivity {

    //Setting up variables for the spinner and textview
    private Spinner info_spinner;
    private TextView info_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Initialising the text view
        info_show = (TextView) findViewById(R.id.info_show);

        // Calls methods to add the options to the spinner and to monitor what is selected
        addTextToInfoSpinner();
        addListenerToSpinner();

    }

    // Adds the options to the spinner on the information page
    private void addTextToInfoSpinner(){

        // Initialising spinner
        info_spinner = (Spinner) findViewById(R.id.info_spinner);

        ArrayAdapter<CharSequence> infoSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.information_categories, android.R.layout.simple_spinner_item);
        infoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_spinner.setAdapter(infoSpinnerAdapter);
    }

    // Listener to monitor what is selected by the user
    private void addListenerToSpinner(){
        info_spinner = (Spinner) findViewById(R.id.info_spinner);

        info_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                if(selectedItem.equals("About Rest Easy")) {
                    info_show.setText(R.string.about);
                }
                else if(selectedItem.equals("Insomnia Information")){
                    info_show.setText(R.string.insomnia);
                }
                else if(selectedItem.equals("Causes of Insomnia")){
                    info_show.setText(R.string.causes);
                }
                else if(selectedItem.equals("Potential Treatments")){
                    info_show.setText(R.string.treatments);
                }
                else if(selectedItem.equals("Symptoms")){
                    info_show.setText(R.string.symptoms);
                }
                else if(selectedItem.equals("Legal Information")){
                    info_show.setText(R.string.legal);
                }
                else if(selectedItem.equals("Credits")){
                    info_show.setText(R.string.credits);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                info_show.setText(R.string.selection_needed);
            }
        });
        }
    }
