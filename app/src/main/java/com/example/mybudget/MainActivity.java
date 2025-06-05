package com.example.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybudget.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button nameEntryButton;
    private EditText nameEntryEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_page);

        mapping();
        databaseHelper = new DatabaseHelper(this);

        checkExistingUser();

        setUpInsertUsername();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void mapping() {
        this.nameEntryButton = findViewById(R.id.buttonEnterName);
        this.nameEntryEditText = findViewById(R.id.nameEntryEditText);
    }

    public void setUpInsertUsername() {
        nameEntryButton.setOnClickListener(v -> {
            String name = nameEntryEditText.getText().toString().trim();
            if (!name.isEmpty()) {
                try {
                    databaseHelper.insertUsername(name);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkExistingUser() {
        String savedName = databaseHelper.getLatestUsername();

        if (savedName != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("username", savedName);
            startActivity(intent);
            finish();
        }
    }
}