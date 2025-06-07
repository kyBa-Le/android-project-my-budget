package com.example.mybudget.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;

import com.example.mybudget.CategoryActivity;
import com.example.mybudget.HistoryActivity;
import com.example.mybudget.HomeActivity;
import com.example.mybudget.R;

public class MenuComponent {
    public static void setupMenu(Context context, ImageView menuIcon) {
        menuIcon.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, menuIcon);
            popup.getMenuInflater().inflate(R.menu.activity_main_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> handleMenuClick(context, item));
            popup.show();
        });
    }

    private static boolean handleMenuClick(Context context, MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.your_budget) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.category) {
            Intent intent = new Intent(context, CategoryActivity.class);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.history) {
            Intent intent = new Intent(context, HistoryActivity.class);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.close) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;

                new AlertDialog.Builder(context)
                        .setTitle("Exit App")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            activity.finishAffinity(); // Close all activities
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            return true;
        } else {
            return false;
        }
    }
}
