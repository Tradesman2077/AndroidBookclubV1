package com.example.compscilibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookView extends AppCompatActivity {
    private TextView titleTextView;
    private TextView authorTextView;
    private TextView subjectTextView;
    private TextView isbnTextView;
    private Button reviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        getApplicationContext();
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String subject = getIntent().getStringExtra("subject");
        String isbn = getIntent().getStringExtra("isbn");

        titleTextView = findViewById(R.id.title_text_view_book);
        authorTextView = findViewById(R.id.author_text_view_book);
        subjectTextView = findViewById(R.id.subject_text_view_book);
        isbnTextView = findViewById(R.id.isbn_text_view_book);
        reviewButton = findViewById(R.id.review_button);

        titleTextView.setText(title);
        titleTextView.setTextSize(20);
        authorTextView.setText(author);
        authorTextView.setTextSize(20);
        subjectTextView.setText(subject);
        subjectTextView.setTextSize(20);
        isbnTextView.setText(isbn);
        isbnTextView.setTextSize(20);


        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(BookView.this, ReviewPage.class);
                reviewIntent.putExtra("title", title);
                startActivity(reviewIntent);
            }
        });
    }
}