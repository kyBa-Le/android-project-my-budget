package com.example.mybudget.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_TRANSACTION = "transactions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USER + "( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT)";
        initializeCategoryTable(db);
        initializeTransactionTable(db);
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }

    private void initializeCategoryTable(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "( id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT)";
        db.execSQL(CREATE_CATEGORIES_TABLE);

        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (category) VALUES ('Salary')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (category) VALUES ('Transport')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (category) VALUES ('Shopping')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (category) VALUES ('Food')");
    }

    private void initializeTransactionTable(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL NOT NULL, " +
                "category_id INTEGER NOT NULL, " +
                "type TEXT CHECK(type IN ('income', 'spending')) NOT NULL, " +
                "description TEXT, " +
                "date TEXT NOT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES categories(id)" +
                ");";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

}
