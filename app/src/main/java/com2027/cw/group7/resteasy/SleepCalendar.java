package com2027.cw.group7.resteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

//import javax.xml.crypto.Data;

/**
 * https://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65
 */
public class SleepCalendar extends AppBaseActivity {
    ListView listView ;

    private ArrayList<String> details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final SleepCalendar context = this;
        if (user == null) return;

        SleepData.loadAll(user.getUid()).addOnCompleteListener(
        new OnCompleteListener<Map<String, SleepData>>() {
           @Override
           public void onComplete(@NonNull Task<Map<String, SleepData>> t) {
               Map<String, SleepData> map = t.getResult();
               Log.d("RESTEASY_SleepCal", "Map Size: " + map.size());

               ArrayList<String> values = new ArrayList<String>();
               details = new ArrayList<String>();
               NumberFormat format = new DecimalFormat("#0.0"); // Only show 1 decimal point for sleep time

               for (Map.Entry<String, SleepData> entry : map.entrySet()) {

                   SleepData sd = entry.getValue();
                   values.add("Sleep date: " +sd.date+ "\nRating: " + sd.sleepRating + "% | Length: " + sd.userSleepTime + " hours");
                   details.add("\nTreatment: " + sd.treatment + "\nComment: " + sd.comment);
               }
               setContentView(R.layout.activity_sleep_calendar);

               // Get ListView object from xml
               listView = (ListView) findViewById(R.id.list_calendar);

               // Define a new Adapter
               // First parameter - Context
               // Second parameter - Layout for the row
               // Third parameter - ID of the TextView to which the data is written
               // Forth - the Array of data
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                       android.R.layout.simple_list_item_1, android.R.id.text1, values);

               // Assign adapter to ListView
               listView.setAdapter(adapter);


               // ListView Item Click Listener
               listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                   @Override
                   public void onItemClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                       // ListView Clicked item index
                       int itemPosition     = position;

                       // ListView Clicked item value
                       String  itemValue    = (String) listView.getItemAtPosition(position);

                       // Show Alert
                       Toast.makeText(getApplicationContext(), details.get(itemPosition), Toast.LENGTH_LONG)
                               .show();

                   }
               });
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
