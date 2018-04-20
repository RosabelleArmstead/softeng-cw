package com2027.cw.group7.resteasy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * https://www.youtube.com/watch?v=cp2rL3sAFmI
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Sleeps.db";
    public static final String TABLE_NAME = "sleeps_table";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "USERRATING";
    public static final String COL_4 = "SLEEPRATING";
    public static final String COL_5 = "USERSLEEPTIME";
    public static final String COL_6 = "TREATMENT";
    public static final String COL_7 = "COMMENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Date TEXT, UserRating INTEGER, SleepRating INTEGER, UserSleepTime REAL, Treatment TEXT, Comment TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date,int userRating,int sleepRating, double userSleepTime,String treatment, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,userRating);
        contentValues.put(COL_4,sleepRating);
        contentValues.put(COL_5,userSleepTime);
        contentValues.put(COL_6,treatment);
        contentValues.put(COL_7,comment);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}