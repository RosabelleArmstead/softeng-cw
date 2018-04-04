package com2027.cw.group7.resteasy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class for creating, upgrading and managing the Local database that stores the user's sleep data
 *
 * Created with help from COM1032 Lab#7
 *
 * Maybe use ?
 * https://www.youtube.com/watch?annotation_id=annotation_2120971985&feature=iv&src_vid=cp2rL3sAFmI&v=p8TaTgr4uKM
 */
public class DBManager extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1; //Auxiliary data variable describing the version of the DB
    private final static String SLEEP_TABLE_NAME = "sleep"; // Name of the table that will be created within our DB

    /**
     * Constructor for setting the SQLiteOpenHelper object...
     * It simply calls the super constructor with the necessary parameters
     *
     * @param context The context of the activity that will create this DB helper object
     * @param name The name of this DB helper for referencing it in the code
     * @param factory DB helper pool for basically providing instance of the Cursor objects to access the DB
     */
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /**
     * Invoke the createTable() method for creating the sleep table within the DB
     * @param sqLiteDatabase Reference to the SQLiteDatabase object for being able to execute SQL commands
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d( "[CountryDB::onCreate]", "Creating countries table" );
        createTable(sqLiteDatabase);
    }

    private void createTable(SQLiteDatabase sqLiteDatabase) {
        String createSQL = "CREATE TABLE " + SLEEP_TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date TEXT, " +
                "UserRating INTEGER, " +
                "SleepRating INTEGER, " +
                "UserSleepTime REAL, " +
                "Treatment TEXT);";
        //Execute the SQL creation clause...Since we don't expect any results back, we call the execSQL and not the rawQuery
        sqLiteDatabase.execSQL( createSQL );
    }


    /**
     * Method that is invoked when we want to upgrade the version of the DB.
     * This means that all the existing tables have to be deleted adn re-constructed following the new format of the DB.
     *
     * @param db Reference to the SQLiteDatabase object for being able to execute SQL commands
     * @param oldVersion The old version of the DB
     * @param newVersion The new version of the DB
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // First of all check to see if the countries table already exist..if this is the case then delete it
        String dropSQL = "DROP TABLE IF EXISTS " + SLEEP_TABLE_NAME+ ";";
        db.execSQL( dropSQL );

        //Then create the table again so that the new DB version will be adopted... */
        createTable(db);
        Log.d("[CountryDB::onUpgrade]", "Upgrading DB");
    }

    /**
     * Method for deleting all the contents of the countries table.
     * There are many ways of doing that: delete all the rows of the table, delete the table and then re-create
     it so that it is empty, etc. In this case, we are going with the second solution
     * as it is more efficient
     */
    public void clearData() {
        //Get a reference to the DB so that we can update it
        SQLiteDatabase db = this.getWritableDatabase();
        //First of all check to see if the countries table already exist..if this is the case then delete it
        String dropSQL = "DROP TABLE IF EXISTS " + SLEEP_TABLE_NAME + ";";
        db.execSQL(dropSQL);
        //Then create the table again so that the new DB version will be adopted
        createTable(db);
    }

    public void insertData(String date, int userRating, String sleepRating , double userSleepTime, String treatment) {
        //Returns the DB associated with this helper
        SQLiteDatabase dbCountries = this.getWritableDatabase();
        String insertSQL = "INSERT INTO " + SLEEP_TABLE_NAME+ " (Date, UserRating, SleepRating, UserSleepTime, Treatment) VALUES('" + date + "','" + userRating +"','" + sleepRating +"','"+userSleepTime+"','"+treatment+"')";
        dbCountries.execSQL(insertSQL);
        this.close();
    }
/*
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + SLEEP_TABLE_NAME, null);
    }
*/
}
