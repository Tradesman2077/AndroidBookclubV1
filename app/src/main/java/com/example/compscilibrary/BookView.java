package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BookView extends AppCompatActivity {

    ///activity where a single book is viewed

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView subjectTextView;
    private TextView isbnTextView;
    private Button reviewButton;
    public static final String REVIEW_ID = "Q22fNKJZQ7cQz11oZ9wg";
    private LinearLayout reviewLayout;

    //Connection to firestore

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference book_db =
            db.collection("CompSci_Db")
                    .document("books").collection("book_list");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        getApplicationContext();

        ///get book details
        Book currentBook = new Book(getIntent().getStringExtra("title"), getIntent().getStringExtra("author"),
                getIntent().getStringExtra("isbn"), getIntent().getStringExtra("subject"));

        //set ids for views
        titleTextView = findViewById(R.id.title_text_view_book);
        authorTextView = findViewById(R.id.author_text_view_book);
        subjectTextView = findViewById(R.id.subject_text_view_book);
        isbnTextView = findViewById(R.id.isbn_text_view_book);
        reviewButton = findViewById(R.id.review_button);
        reviewLayout  =findViewById(R.id.reviews_layout);

        //add book details to views
        titleTextView.setText(currentBook.getTitle());
        titleTextView.setTextSize(20);
        authorTextView.setText(currentBook.getAuthor());
        authorTextView.setTextSize(20);
        subjectTextView.setText(currentBook.getSubject());
        subjectTextView.setTextSize(20);
        isbnTextView.setText(currentBook.getISBN());

        //return reviews for currentBook from db and add to scroll view
        getReviews(currentBook.getTitle());


        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(BookView.this, ReviewPage.class);
                reviewIntent.putExtra("title", currentBook.getTitle());
                startActivity(reviewIntent);
            }
        });
    }


    //method to return reviews for book and add to scroll view
    public void getReviews(String title){
        book_db.document(REVIEW_ID).collection("reviews").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    if (snapshots.getString(title) != null){
                        //add to a new text view and add to scrollview
                        TextView newTextView = new TextView(getApplicationContext());
                        newTextView.setText(String.format("'%s'", snapshots.getString(title)));
                        reviewLayout.addView(newTextView);
                        newTextView.setTextSize(18);
                        newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("reviews error", "onFailure: "+e);
            }
        });
    };
}