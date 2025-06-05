package com.example.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybudget.component.MenuComponent;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTextView;
    ImageView menuIcon;
    EditText totalBudgetEditText, expectedBudgetEditText;
    Button incomeButton, spendingButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        mapping();
        MenuComponent.setupMenu(this, menuIcon);

        String username = getIntent().getStringExtra("username");
        usernameTextView.setText(username);

        setUpIncomeButton();
        setUpSpendingButton();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPaddingRelative(20, systemBars.top, 20, systemBars.bottom);
            return insets;
        });

    }

    private void mapping() {
        usernameTextView = findViewById(R.id.textViewUsername);
        menuIcon = findViewById(R.id.menu_icon);
        totalBudgetEditText = findViewById(R.id.totalBudgetEditText);
        expectedBudgetEditText = findViewById(R.id.expectedBudgetEditText);
        incomeButton = findViewById(R.id.incomeButton);
        spendingButton = findViewById(R.id.spendingButton);
    }

    public void setUpIncomeButton() {
        incomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, IncomeFormActivity.class);
            startActivity(intent);
        });
    }

    public void setUpSpendingButton() {
        spendingButton.setOnClickListener(v -> {
            Toast.makeText(this, "You tapped on spending button", Toast.LENGTH_SHORT).show();
        });
    }
}
