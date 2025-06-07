package com.example.mybudget.database;

import android.content.ContentValues;
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

    public void createCategory(String category) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sql = "INSERT INTO categories (category) VALUES (?)";
        db.execSQL(sql, new String[]{category});
    }

    public Category getCategoryById(int categoryId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"id", "category"};
        String query = "SELECT * FROM categories WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            return new Category(category, id);
        } else {
            return null;
        }
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category", category.getCategory());
        db.update("categories", values, "id = ?", new String[]{String.valueOf(category.getId())});
        db.close();
    }

    public void deleteCategory(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("categories", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
