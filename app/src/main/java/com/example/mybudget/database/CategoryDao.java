package com.example.mybudget.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mybudget.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private DatabaseHelper databaseHelper;

    public CategoryDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public List<Category> getAllCategories() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"id", "category"};
        Cursor cursor = db.query("categories", columns, null, null, null, null, null);
        List<Category> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                categories.add(new Category(category, id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }
}
