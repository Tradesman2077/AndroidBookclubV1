package com.example.compscilibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class HomeScreen extends AppCompatActivity {

    private static final String TAG = "HomeScreen";
    private TextView userNameText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        String currentUser = getIntent().getExtras().getString("user email");
        User currentUserObj = new User(currentUser);

        userNameText = findViewById(R.id.username_text_view);
        searchButton = findViewById(R.id.search_book_button);

        userNameText.setText(currentUserObj.getEmail());




    }
}