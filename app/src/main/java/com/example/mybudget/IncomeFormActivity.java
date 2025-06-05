package com.example.mybudget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybudget.database.CategoryDao;
import com.example.mybudget.database.TransactionDao;
import com.example.mybudget.entity.Category;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IncomeFormActivity extends AppCompatActivity {

    EditText amountEditText, descriptionEditText;
    TextView dateTextView;
    Spinner categorySpinner;
    Button buttonSave;

    private CategoryDao categoryDao;
    private TransactionDao transactionDao;

    String selectedDateTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_form);

        mapping();

        categoryDao = new CategoryDao(this);
        transactionDao = new TransactionDao(this);

        createCategorySpinner();
        setupDateTimePicker();
        setupSaveButton();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPaddingRelative(20, systemBars.top, 20, systemBars.bottom);
            return insets;
        });

    }

    public void mapping() {
        amountEditText = findViewById(R.id.editTextAmount);
        descriptionEditText = findViewById(R.id.editTextDescription);
        dateTextView = findViewById(R.id.textViewSelectDate);
        categorySpinner = findViewById(R.id.spinnerCategory);
        buttonSave = findViewById(R.id.buttonSave);
    }

    public void createCategorySpinner() {
        List<Category> categories = categoryDao.getAllCategories();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void setupDateTimePicker() {
        dateTextView.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            new DatePickerDialog(this, (view, year, month, day) -> {
                new TimePickerDialog(this, (view1, hour, minute) -> {
                    calendar.set(year, month, day, hour, minute);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    selectedDateTime = sdf.format(calendar.getTime());
                    dateTextView.setText(selectedDateTime);
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    public void setupSaveButton() {
        buttonSave.setOnClickListener(v -> {
            try {
                saveTransaction();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, selectedDateTime, Toast.LENGTH_SHORT).show();
        });
    }

    public void saveTransaction() {
        long amount = Long.parseLong(amountEditText.getText().toString());
        String description = descriptionEditText.getText().toString();
        Category category = (Category) categorySpinner.getSelectedItem();
        String date = dateTextView.getText().toString();
        transactionDao.createTransaction(amount, category.getId(), "income", description, date);
    }

}