package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RoutineDatabase extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Classes";
    private static final String COL1 = "ID";
    private static final String COL2 = "Code";
    private static final String COL3 = "building";
    private static final String COL4 = "className";
    private static final String COL5 = "classroom";
    private static final String COL6 = "day1";
    private static final String COL7 = "ending1";
    private static final String COL8 = "starting1";
    private static final String COL9 = "teacher";
    private static final String COL10 = "CRN";
    private static final String COL11 = "Attendence";
    private static final String create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT NOT NULL, " + COL5 + " TEXT, " +
            COL6 + " TEXT, " + COL7 +" TEXT, " + COL8 + " TEXT NOT NULL, " + COL9 + " TEXT, " + COL10 + " TEXT, " + COL11 + " INTEGER DEFAULT 0)";


    public RoutineDatabase(Context context) {
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

    public long addData(String Code, String building, String className, String classroom, String day1,
                           String ending1, String starting1, String teacher, String crn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL10, crn);
        contentValues.put(COL2, Code);
        contentValues.put(COL3, building);
        contentValues.put(COL4, className);
        contentValues.put(COL5, classroom);
        contentValues.put(COL6, day1);
        contentValues.put(COL7, ending1);
        contentValues.put(COL8, starting1);
        contentValues.put(COL9, teacher);

        return db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
    }

    public long updateData(int id, String building, String className, String classroom, String day1,
                           String ending1, String starting1, String teacher, String crn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL10, crn);
        contentValues.put(COL3, building);
        contentValues.put(COL4, className);
        contentValues.put(COL5, classroom);
        contentValues.put(COL6, day1);
        contentValues.put(COL7, ending1);
        contentValues.put(COL8, starting1);
        contentValues.put(COL9, teacher);

        String where = "ID = ?";
        String[] args = new String[1];
        args[0] = String.valueOf(id);

        return db.update(TABLE_NAME, contentValues, where, args);

        //if date as inserted incorrectly it will return -1

    }

    public void updateAttendance(int absence, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL11 +
                " = '" + absence + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + absence);
        db.execSQL(query);
    }

    public void updateClassName(String classname, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + classname + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + classname);
        db.execSQL(query);
    }

    public void updateDay(String day, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL11 +
                " = '" + day + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + day);
        db.execSQL(query);
    }

    public void updateStartTime(String start, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL8 +
                " = '" + start + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + start);
        db.execSQL(query);
    }

    public void updateEndTime(String end, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL7 +
                " = '" + end + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + end);
        db.execSQL(query);
    }

    public void updateBuilding(String building, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + building + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + building);
        db.execSQL(query);
    }

    public void updateClassroom(String classroom, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + classroom + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + classroom);
        db.execSQL(query);
    }

    public void updateTeacher(String teacher, int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL9 +
                " = '" + teacher + "' WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        Log.d(TAG, "updateAttendance: query: " + query);
        Log.d(TAG, "updateName: Setting attendance to " + teacher);
        db.execSQL(query);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor getCRNs(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL10 + " FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }


    public Cursor getRowByCRN(int crn){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL10 + " = '" + String.valueOf(crn) + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getRowByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + id;
        return db.rawQuery(query, null);
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getRowsByDay(String day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL6 + " = '" + day + "' ORDER BY " + COL8;
        return db.rawQuery(query, null);
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    public void deleteById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = " + id;
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }

}