package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class ReviewPage extends AppCompatActivity {



    //Connection to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference book_db =
            db.collection("CompSci_Db")
                    .document("books").collection("book_list");
    private CollectionReference news_db = db.collection("CompSci_Db")
            .document("news").collection("changes");


    private Button submitReviewButton;
    private EditText reviewContentEditText;
    // ref to collection in db storing reviews
    public static final String REVIEW_ID = "Q22fNKJZQ7cQz11oZ9wg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        submitReviewButton = findViewById(R.id.submit_review_button);
        reviewContentEditText = findViewById(R.id.reviewContentEditText);

        //get title of book being reviewed and current user
        String title = getIntent().getStringExtra("title");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String  user = sharedPreferences.getString("user", "def") ;

        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            //add review
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put(title, reviewContentEditText.getText().toString());
                Task<DocumentReference> doc = book_db.document(REVIEW_ID).collection("reviews").add(data);
                Map<String, Object> data2 = new HashMap<>();
                String newsItem = "Reviewed: " + title;
                data2.put(user, newsItem);
                Task<DocumentReference> doc2 = news_db.add(data2).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d("tag", "onComplete: "+ newsItem);
                    }
                });

                Toast.makeText(ReviewPage.this, "Review added.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}