package com.example.mybudget;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        mapping();

        String username = getIntent().getStringExtra("username");
        usernameTextView.setText(username);
    }

    private void mapping() {
        usernameTextView = findViewById(R.id.textViewUsername);
    }
}
