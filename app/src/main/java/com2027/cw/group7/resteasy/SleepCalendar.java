package com2027.cw.group7.resteasy;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.xml.crypto.Data;

/**
 * https://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65
 */
public class SleepCalendar extends AppBaseActivity {
    ListView listView ;
    private DatabaseHelper myDb;
    private android.database.Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<String> details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        sqLiteDatabase = myDb.getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM sleeps_table",
                null);

        ArrayList<String> values = new ArrayList<String>();
        details = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {

                String date = cursor.getString(cursor.getColumnIndex("DATE"));
                String user_rating = cursor.getString(cursor.getColumnIndex("USERRATING"));
                String sleep_rating = cursor.getString(cursor.getColumnIndex("SLEEPRATING"));
                String sleep_duration = cursor.getString(cursor.getColumnIndex("USERSLEEPTIME"));
                String treatment = cursor.getString(cursor.getColumnIndex("TREATMENT"));
                String comment = cursor.getString(cursor.getColumnIndex("COMMENT"));

                values.add(date + " | Rating: " + sleep_rating + " | Length: " + sleep_duration);
                details.add(treatment + "\n" + comment);

            }while(cursor.moveToNext());
        }
        cursor.close();

        setContentView(R.layout.activity_sleep_calendar);
// Get ListView object from xml
        listView = (ListView) findViewById(R.id.list_calendar);


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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

}
