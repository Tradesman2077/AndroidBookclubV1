package com.example.compscilibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeScreen extends AppCompatActivity {

    private static final String TAG = "HomeScreen";
    private TextView userNameText;
    private Button searchButton;
    private Button addBookButton;
    private Button allBooksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getApplicationContext();
        String currentUser = getIntent().getExtras().getString("user email");
        User currentUserObj = new User(currentUser);

        userNameText = findViewById(R.id.username_text_view);
        searchButton = findViewById(R.id.search_book_button);
        addBookButton = findViewById(R.id.add_book_button);
        allBooksButton = findViewById(R.id.all_books_button);

        userNameText.setText(currentUserObj.getEmail());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(HomeScreen.this, Search.class);
                startActivity(searchIntent);
            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBookIntent = new Intent(HomeScreen.this, Add_book_activity.class);
                startActivity(addBookIntent);
            }
        });
        allBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allBooksIntent = new Intent(HomeScreen.this, AllBooks.class);
                startActivity(allBooksIntent);
            }
        });



    }
}