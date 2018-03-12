package com2027.cw.group7.resteasy;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * https://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65
 */
public class SleepCalendar extends AppBaseActivity {
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_calendar);
// Get ListView object from xml
        listView = (ListView) findViewById(R.id.list_calendar);

        // Defined Array values to show in ListView
        String[] values = new String[] { "1/3/2018 | Rating:4/5 | Length: 5 hours",
                "2/3/2018 | Rating:3/5 | Length: 3 hours",
                "3/3/2018 | Rating:3/5 | Length: 4 hours",
                "4/3/2018 | Rating:4/5 | Length: 6 hours",
                "5/3/2018 | Rating:5/5 | Length: 9 hours",
                "6/3/2018 | Rating:3/5 | Length: 5 hours",
                "7/3/2018 | Rating:2/5 | Length: 2 hours",
                "8/3/2018 | Rating:2/5 | Length: 2 hours"
        };

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
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

}
