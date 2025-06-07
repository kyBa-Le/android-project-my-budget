package com.example.mybudget;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybudget.component.MenuComponent;
import com.example.mybudget.database.BudgetSharedPreference;
import com.example.mybudget.util.MoneyFormatter;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTextView;
    ImageView menuIcon;
    EditText totalBudgetEditText, expectedBudgetEditText;
    Button incomeButton, spendingButton;
    private BudgetSharedPreference budgetSharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        mapping();
        MenuComponent.setupMenu(this, menuIcon);

        String username = getIntent().getStringExtra("username");
        usernameTextView.setText(username);

        budgetSharedPreference = BudgetSharedPreference.getInstance(this);
        totalBudgetEditText.setText(MoneyFormatter.formatVND(budgetSharedPreference.getTotalBudget()));
        expectedBudgetEditText.setText(MoneyFormatter.formatVND(budgetSharedPreference.getExpectedBudget()));

        setUpIncomeButton();
        setUpSpendingButton();
        setupExpectedInput();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPaddingRelative(20, systemBars.top, 20, systemBars.bottom);
            return insets;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        long updatedBudget = this.budgetSharedPreference.getTotalBudget();
        totalBudgetEditText.setText(MoneyFormatter.formatVND(updatedBudget));

        long updatedExpectedBudget = this.budgetSharedPreference.getExpectedBudget();
        expectedBudgetEditText.setText(MoneyFormatter.formatVND(updatedExpectedBudget));
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
            Intent intent = new Intent(HomeActivity.this, ExpenditureFormActivity.class);
            intent.putExtra("type", "income");
            startActivity(intent);
        });
    }

    public void setUpSpendingButton() {
        spendingButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ExpenditureFormActivity.class);
            intent.putExtra("type", "spending");
            startActivity(intent);
        });
    }


    public void setupExpectedInput() {
        expectedBudgetEditText.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_input_expected_budget, null);
            EditText expectedBudgetEditTextDialog = dialogView.findViewById(R.id.expectedBudgetEditTextDialog);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);

            builder.setPositiveButton("SAVE", (dialog, which) -> {
                String expectedBudgetText = expectedBudgetEditTextDialog.getText().toString();
                if (!expectedBudgetText.isEmpty()) {
                    long expectedBudget = Long.parseLong(expectedBudgetText);
                    budgetSharedPreference.setExpectedBudget(expectedBudget);
                    expectedBudgetEditText.setText(MoneyFormatter.formatVND(expectedBudget));
                }

            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> {
                dialog.dismiss();
            });

            Dialog dialog = builder.create();
            dialog.show();
        });
    }
}
