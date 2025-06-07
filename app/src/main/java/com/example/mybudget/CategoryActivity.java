package com.example.mybudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybudget.component.MenuComponent;
import com.example.mybudget.database.CategoryDao;
import com.example.mybudget.entity.Category;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ImageView menuIcon;
    private ListView categoryListView;
    private Button addCategoryButton;
    private List<Category> categoryList;
    private CategoryDao categoryDao;
    private ArrayAdapter<Category> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        mapping();

        MenuComponent.setupMenu(this, menuIcon);
        categoryDao = new CategoryDao(this);

        getAllCategories();

        setupAddCategoryButton();
        setupCategoryLongClick();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(20, systemBars.top, 20, systemBars.bottom);
            return insets;
        });
    }

    private void mapping() {
        categoryListView = findViewById(R.id.categoryListView);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        menuIcon = findViewById(R.id.menu_icon);
    }

    private void getAllCategories() {
        categoryList = categoryDao.getAllCategories();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);
        categoryListView.setAdapter(adapter);
    }

    private void setupAddCategoryButton() {
        addCategoryButton.setOnClickListener(v -> {
            showDialogAddCategory();
        });
    }

    private void showDialogAddCategory() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_input_category, null);

        EditText editTextAddCategory = dialogView.findViewById(R.id.editTextAddCategory);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add a new category")
                .setPositiveButton("Save", (dialog, which) -> {
                    String inputText = editTextAddCategory.getText().toString();
                    if (inputText.isEmpty()) {
                        editTextAddCategory.setError("Please enter a category");
                    } else {
                        categoryDao.createCategory(inputText);
                        getAllCategories();
                        Toast.makeText(this, "Category added", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();


    }

    private void setupCategoryLongClick() {
        categoryListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Category selectedCategory = categoryList.get(position);
            showEditOrDeleteDialog(selectedCategory);
            return true;
        });
    }

    private void showEditOrDeleteDialog(Category category) {
        String[] options = {"Update", "Delete"};

        new AlertDialog.Builder(this)
                .setTitle("Select for \"" + category.getCategory() + "\"")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showEditCategoryDialog(category);
                    } else if (which == 1) {
                        showDeleteConfirmDialog(category);
                    }
                })
                .show();
    }

    private void showEditCategoryDialog(Category category) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_input_category, null);

        EditText editText = dialogView.findViewById(R.id.editTextAddCategory);
        editText.setText(category.getCategory());

        new AlertDialog.Builder(this)
                .setTitle("Edit category")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editText.getText().toString().trim();
                    if (!newName.isEmpty()) {
                        category.setCategory(newName);
                        categoryDao.updateCategory(category);
                        getAllCategories();
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmDialog(Category category) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete")
                .setMessage("Would you like to delete category \"" + category.getCategory() + "\" ?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    categoryDao.deleteCategory(category.getId()); // Cần có hàm này
                    getAllCategories();
                    Toast.makeText(this, "Category deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }



}