package com2027.cw.group7.resteasy;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Information extends AppBaseActivity {

    private Spinner info_spinner;
    private TextView info_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        info_show = (TextView) findViewById(R.id.info_show);

        addTextToInfoSpinner();
        addListenerToSpinner();

    }

    private void addTextToInfoSpinner(){

        info_spinner = (Spinner) findViewById(R.id.info_spinner);

        ArrayAdapter<CharSequence> infoSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.information_categories, android.R.layout.simple_spinner_item);
        infoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info_spinner.setAdapter(infoSpinnerAdapter);
    }

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                info_show.setText(R.string.selection_needed);
            }
        });
        }
    }
