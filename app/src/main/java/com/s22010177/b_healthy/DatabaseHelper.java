package com.s22010177.b_healthy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Declaring the database
    public static final String DATABASE_NAME = "bHealthy.db";

    // ----------- Declaring 1st Table, with its columns ----------
    public static final String TABLE_1 = "user_table";
    public static final String user_col_1 = "user_ID"; //PK
    public static final String user_col_2 = "username";
    public static final String user_col_3 = "password";

    // ------------ Declaring 2nd Table, with its columns ----------
    public static final String TABLE_2 = "notes_table";
    public static final String note_col_1 = "note_ID"; //PK
    public static final String note_col_2 = "date";
    public static final String note_col_3 = "BMI";
    public static final String note_col_4 = "step_count";
    public static final String note_col_5 = "sleeping_hours";
    public static final String note_col_6 = "user_ID"; //FK


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_1 + " (user_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, password TEXT)");

        db.execSQL("create table " + TABLE_2 + " (note_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, BMI TEXT, step_count TEXT, sleeping_hours TEXT, user_ID INTEGER, " +
                "FOREIGN KEY(user_ID) REFERENCES " + TABLE_1 + "(user_ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        onCreate(db);
    }

    //------------------------------ Methods of 1st Table ------------------------------------------

    // Method to Insert data for table - 01
    public boolean insertUserData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(user_col_2, username);
        contentValues.put(user_col_3, password);
        long result = db.insert(TABLE_1, null, contentValues);
        return result != -1;
    }

    // Method to check if username already exists from table 1
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {user_col_1};
        String selection = user_col_2 + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_1, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Method to Search data from table - 01
    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {user_col_1};
        String selection = user_col_2 + " = ?" + " AND " + user_col_3 + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_1, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Method to retrieve User_ID of the logged user
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_ID FROM " + TABLE_1 +
                " WHERE " + user_col_2 + "=? " + "AND " + user_col_3 + "=?", new String[]{username, password});
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(user_col_1));
            cursor.close();
            return userId;
        }
        return -1;
    }

    //------------------------------ Methods of 2nd Table ------------------------------------------

    // Method to Insert data for table - 02
    public boolean insertNote(String date, String bmi, String stepCount, String sleepingHours, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(note_col_2, date);
        contentValues.put(note_col_3, bmi);
        contentValues.put(note_col_4, stepCount);
        contentValues.put(note_col_5, sleepingHours);
        contentValues.put(note_col_6, userId);
        long result = db.insert(TABLE_2, null, contentValues);
        return result != -1;
    }

    // Method to Search data for table - 02
    public Cursor viewAllNotes(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select * from " + TABLE_2 + " where user_ID = ?",
                new String[]{String.valueOf(userId)});
        return results;
    }

    // Method to Update data for table - 02
    public boolean updateNote(String noteId, String date, String bmi, String stepCount, String sleepingHours) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(note_col_1, noteId);
        contentValues.put(note_col_2, date);
        contentValues.put(note_col_3, bmi);
        contentValues.put(note_col_4, stepCount);
        contentValues.put(note_col_5, sleepingHours);
        db.update(TABLE_2, contentValues, "note_ID = ?", new String[]{noteId});
        return true;
    }

    // Method to Delete data for table - 02
    public Integer deleteData(String noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_2, "note_ID = ?", new String[]{noteId});
    }
}