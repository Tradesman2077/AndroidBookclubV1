package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        resultsLayout = findViewById(R.id.resultsLayout);

       book_db.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                    String title = snapshots.get("title").toString();
                    String author = snapshots.get("author").toString();
                    String subject = snapshots.get("subject").toString();
                    String isbn = snapshots.get("ISBN").toString();

                    //add to a new text view and add to scrollview
                    TextView newTextView = new TextView(getApplicationContext());
                    newTextView.setText(String.format("%s\n%s\n%s\n%s\n", title, author, subject, isbn));
                    resultsLayout.addView(newTextView);
                    newTextView.setTextSize(18);
                    newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    newTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent viewBookIntent = new Intent(AllBooks.this, BookView.class);
                            viewBookIntent.putExtra("title", title);
                            viewBookIntent.putExtra("author", author);
                            viewBookIntent.putExtra("subject", subject);
                            viewBookIntent.putExtra("isbn", isbn);
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
    }
}