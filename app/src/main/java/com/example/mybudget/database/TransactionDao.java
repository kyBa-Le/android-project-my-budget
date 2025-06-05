package com.example.mybudget.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mybudget.entity.Transaction;

public class TransactionDao {
    private DatabaseHelper databaseHelper;

    public TransactionDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void createTransaction(long amount,
                                  int category_id,
                                  String type,
                                  String description,
                                  String date) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("INSERT INTO transactions (amount, category_id, type, description, date) VALUES (?, ?, ?, ?, ?)",
                new Object[]{amount, category_id, type, description, date});
        db.close();
    }


}
