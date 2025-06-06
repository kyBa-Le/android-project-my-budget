package com.example.mybudget;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mybudget.component.CustomHistoryAdapter;
import com.example.mybudget.component.MenuComponent;
import com.example.mybudget.database.TransactionDao;
import com.example.mybudget.entity.Transaction;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView menuIcon;
    private CustomHistoryAdapter adapter;
    private List<Transaction> transactions;
    private TransactionDao transactionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        mapping();
        MenuComponent.setupMenu(this, menuIcon);
        transactionDao = new TransactionDao(this);
        setupListView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(20, systemBars.top, 20, systemBars.bottom);
            return insets;
        });
    }

    private void mapping() {
        listView = findViewById(R.id.history_list_view);
        menuIcon = findViewById(R.id.menu_icon);
    }

    private void setupListView() {
        transactions = transactionDao.getTransactions();
        adapter = new CustomHistoryAdapter(this, transactions, R.layout.custom_history_item);
        listView.setAdapter(adapter);
    }
}