package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class  AllBooks extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference book_db =
            db.collection("CompSci_Db")
                    .document("books").collection("book_list");



    public static final String TAG = "all_books";
    private LinearLayout resultsLayout;
    private List Booklist = new ArrayList<Book>();
    private boolean greyTextBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        resultsLayout = findViewById(R.id.resultsLayout);
        getAllBooks();

    }

    //method to display all books
    private void getAllBooks(){
        book_db.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                    Book book = new Book( snapshots.get("title").toString(), snapshots.get("author").toString(), snapshots.get("subject").toString(), snapshots.get("ISBN").toString());

                    //add to a new text view and add to scrollview
                    TextView newTextView = new TextView(getApplicationContext());
                    newTextView.setText(String.format("\n%s\n%s\n%s\n%s\n", book.getTitle(), book.getAuthor(), book.getSubject(), book.getISBN()));
                    resultsLayout.addView(newTextView);
                    newTextView.setTextSize(18);

                    //sets alternating colours on textviews backgorunds
                    if (greyTextBackground){
                        newTextView.setBackgroundColor(Color.rgb(234, 234, 234));
                        greyTextBackground =false;
                    }
                    else {
                        greyTextBackground = true;
                    }
                    newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    newTextView.setOnClickListener(new View.OnClickListener() {
                        //on click for book view
                        @Override
                        public void onClick(View v) {
                            Intent viewBookIntent = new Intent(AllBooks.this, BookView.class);
                            viewBookIntent.putExtra("title", book.getTitle());
                            viewBookIntent.putExtra("author", book.getAuthor());
                            viewBookIntent.putExtra("subject", book.getSubject());
                            viewBookIntent.putExtra("isbn", book.getISBN());
                            startActivity(viewBookIntent);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
    };
}