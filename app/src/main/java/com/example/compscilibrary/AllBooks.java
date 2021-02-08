package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AllBooks extends AppCompatActivity {



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
                    TextView newTextView = new TextView(getApplicationContext());
                    resultsLayout.addView(newTextView);
                    newTextView.setText(Objects.requireNonNull(snapshots.get("title")).toString());
                    newTextView.setTextSize(18);
                    newTextView.setTextAlignment(newTextView.TEXT_ALIGNMENT_CENTER);
                    //Log.d(TAG, snapshots.getString("title"));


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