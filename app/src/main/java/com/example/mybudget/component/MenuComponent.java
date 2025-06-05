package com.example.mybudget.component;

import android.content.Context;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

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
            Toast.makeText(context, "Your budget", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.category) {
            Toast.makeText(context, "Category", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.history) {
            Toast.makeText(context, "History", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }
}
