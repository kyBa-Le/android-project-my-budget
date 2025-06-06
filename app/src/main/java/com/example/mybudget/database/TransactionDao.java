package com.example.mybudget.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mybudget.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

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

    public List<Transaction> getTransactions() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query("transactions", new String[]{"id", "amount", "category_id", "type", "description", "date"}, null, null, null, null, null);
        List<Transaction> transactions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return transactions;
    }


}
