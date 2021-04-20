package com.skyhope.eventcalenderlibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ActivityDatabase extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Activities";
    private static final String COL1 = "ID";
    private static final String COL2 = "activity_name";
    private static final String COL3 = "CRN";
    private static final String COL4 = "alarm_time";
    private static final String COL5 = "notification_time";
    private static final String COL6 = "day_before";
    private static final String COL7 = "explanation";
    private static final String COL8 = "date";
    private static final String create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " INTEGER DEFAULT 1, " + COL7 + " TEXT, " + COL8 + " TEXT)";


    public ActivityDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // !!! bu metod çalışmıyor ve silince de hata veriyor
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(create_table);
    }

    public int addData(String activity_name, String crn, String alarm_time, String notify_time, int day_before, String explanation, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, activity_name);
        contentValues.put(COL3, crn);
        contentValues.put(COL4, alarm_time);
        contentValues.put(COL5, notify_time);
        contentValues.put(COL6, day_before);
        contentValues.put(COL7, explanation);
        contentValues.put(COL8, date);

        return (int) db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1

    }

    public Cursor getRowByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + id;
        return db.rawQuery(query, null);
    }

    public Cursor getRowsByDate(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL8 + " = '" + date + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getRowByCRN(String crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = '" + crn + "'";
        return db.rawQuery(query, null);
    }

    public boolean updateData(int id, String activity_name, String alarm_time, String notify_time, int day_before, String explanation, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, activity_name);
        contentValues.put(COL4, alarm_time);
        contentValues.put(COL5, notify_time);
        contentValues.put(COL6, day_before);
        contentValues.put(COL7, explanation);
        contentValues.put(COL8, date);

        String where = "ID = ?";
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        long result = db.update(TABLE_NAME, contentValues, where, args);

        //if date as inserted incorrectly it will return -1
        if (result == -1) return false;
        else return true;

    }

    public long updateDataByCRN(String crn, String activity_name, String explanation, String date, String alarm_time, String notify_time, int day_before) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, activity_name);
        contentValues.put(COL4, alarm_time);
        contentValues.put(COL5, notify_time);
        contentValues.put(COL7, explanation);
        contentValues.put(COL8, date);
        contentValues.put(COL6, day_before);

        String where = COL3 + " = ?";
        String[] args = new String[1];
        args[0] = crn;

        return db.update(TABLE_NAME, contentValues, where, args);
        //if date as inserted incorrectly it will return -1
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }


    public void deleteById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = " + id;
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }

}