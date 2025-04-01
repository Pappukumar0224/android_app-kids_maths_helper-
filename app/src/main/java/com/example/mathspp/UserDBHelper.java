package com.example.mathspp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserData.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SCORES = "quiz_scores";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_PIN = "pin";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, pin INTEGER)");
        db.execSQL("CREATE TABLE quiz_scores (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, score INTEGER, date TEXT, FOREIGN KEY(user_id) REFERENCES users(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    // Insert user data
    public boolean insertUser(String name, int age, String pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_PIN, pin);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Return true if insert is successful
    }

    // Validate user PIN
    public boolean validatePin(String pin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_PIN + " = ?", new String[]{pin});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    // Get the last user who signed up
    public Cursor getLastUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT name FROM users ORDER BY id DESC LIMIT 1", null);
    }

    // Get user ID using PIN
    public int getUserIdByPin(String pin) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE pin = ?", new String[]{pin});

        int userId = -1; // Default value if user not found
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return userId;
    }

    // Save quiz score to the database
    public boolean saveScoreToDatabase(int userId, int finalScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        values.put("user_id", userId);
        values.put("score", finalScore);
        values.put("date", date);

        long result = db.insert(TABLE_SCORES, null, values);
        db.close();
        return result != -1; // Return true if insertion is successful
    }
}
