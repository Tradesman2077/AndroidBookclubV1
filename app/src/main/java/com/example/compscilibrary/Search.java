package com.example.compscilibrary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {

    //Connection to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference book_db =
            db.collection("CompSci_Db")
                    .document("books").collection("book_list");

    private EditText searchTextBox;
    private Button searchButton;
    private String searchString;
    private static final String TAG = "activity_search";
    private LinearLayout searchResultsLayout;
    private TextView initialTextView;
    private boolean resultsFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_search);
        resultsFound  =false;

        searchResultsLayout = findViewById(R.id.results_search_linear_layout);
        searchButton = findViewById(R.id.search_submit_button);
        searchTextBox = findViewById(R.id.seacrh_field);
        initialTextView = findViewById(R.id.results_text_view);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // searches db and uses pattern match to check for key word from search
                searchString = searchTextBox.getText().toString();
                String[] searchStringWordArr = searchString.split(" ");

                book_db.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        searchResultsLayout.removeAllViews();

                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                            String title = snapshots.get("title").toString();
                            String author = snapshots.get("author").toString();
                            String subject = snapshots.get("subject").toString();
                            String isbn = snapshots.get("ISBN").toString();

                            for (String word : searchStringWordArr){
                                Pattern pattern = Pattern.compile(".*" + word.toLowerCase() + ".*");
                                Matcher matcher = pattern.matcher(title.toLowerCase() + author.toLowerCase() + subject.toLowerCase() );
                                if (matcher.find()){

                                    //if a match add a text view to the scroll view
                                    TextView newTextView = new TextView(getApplicationContext());
                                    newTextView.setText(String.format("%s\n%s\n%s\n%s\n", title, author, subject, isbn));
                                    searchResultsLayout.addView(newTextView);
                                    newTextView.setTextSize(18);
                                    newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    newTextView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //on click method for each return book view
                                            Intent viewBookIntent = new Intent(Search.this, BookView.class);
                                            viewBookIntent.putExtra("title", title);
                                            viewBookIntent.putExtra("author", author);
                                            viewBookIntent.putExtra("subject", subject);
                                            viewBookIntent.putExtra("isbn", isbn);
                                            startActivity(viewBookIntent);
                                        }
                                    });
                                }
                            }
                            resultsFound = true;
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+ e);
                    }
                });


            }

        });
        if (!resultsFound){
            TextView newTextView = new TextView(getApplicationContext());
            newTextView.setText(R.string.no_results_found_text);
            searchResultsLayout.addView(newTextView);
            newTextView.setTextSize(18);
            newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

    }
}
