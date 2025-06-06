package com.example.mybudget.component;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.mybudget.R;
import com.example.mybudget.database.CategoryDao;
import com.example.mybudget.entity.Transaction;
import com.example.mybudget.util.MoneyFormatter;

import java.util.List;

public class CustomHistoryAdapter extends ArrayAdapter<Transaction> {
    private final Context context;
    private final List<Transaction> transactions;
    private final int layoutResourceId;

    public CustomHistoryAdapter(Context context, List<Transaction> transactions, int layoutResourceId) {
        super(context, layoutResourceId, transactions);
        this.context = context;
        this.transactions = transactions;
        this.layoutResourceId = layoutResourceId;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        }
        String description = transactions.get(position).getDescription();
        String date = transactions.get(position).getDate();
        long amount = transactions.get(position).getAmount();

        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);

        if (transactions.get(position).getType().equals("income")) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.history_item_color_income));
            amountTextView.setText("+" + MoneyFormatter.formatVND(amount));
            amountTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_purple));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.history_item_color_spending));
            amountTextView.setText("-" + MoneyFormatter.formatVND(amount));
            amountTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_pink));
        }

        descriptionTextView.setText(description);
        dateTextView.setText(date);

        setupOnClick(convertView, transactions.get(position));


        return convertView;
    }

    private void setupOnClick(View convertView, Transaction transaction) {
        convertView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.detailed_transaction);


            EditText amountDetailsEditText = dialog.findViewById(R.id.amountDetailsEditText);
            EditText categoryDetailsEditText = dialog.findViewById(R.id.categoryDetailsEditText);
            EditText descriptionDetailsEditText = dialog.findViewById(R.id.descriptionDetailsEditText);
            EditText dateDetailsEditText = dialog.findViewById(R.id.dateDetailsEditText);

            amountDetailsEditText.setText(MoneyFormatter.formatVND(transaction.getAmount()));
            amountDetailsEditText.setText(MoneyFormatter.formatVND(transaction.getAmount()));
            descriptionDetailsEditText.setText(transaction.getDescription());
            dateDetailsEditText.setText(transaction.getDate());

            CategoryDao categoryDao = new CategoryDao(context);
            String categoryName = categoryDao.getCategoryById(transaction.getCategory_id()).getCategory();
            categoryDetailsEditText.setText(categoryName);

            dialog.setCanceledOnTouchOutside(true);

            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
            }
            dialog.show();
        });
    }
}
