package com.example.mybudget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
    private DatabaseHelper databaseHelper;

    public UserDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long insertUsername(String username) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        return db.insert("users", null, values);
    }

    public String getLatestUsername() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String latestUsername = null;

        Cursor cursor = db.rawQuery("SELECT username FROM users ORDER BY id DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            latestUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            cursor.close();
        }

        db.close();
        return latestUsername;
    }


}
