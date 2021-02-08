package com.example.compscilibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_search);

        searchButton = findViewById(R.id.search_submit_button);
        searchTextBox = findViewById(R.id.seacrh_field);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchString = searchTextBox.getText().toString();

                Query query = book_db.whereEqualTo("title", searchString);
                Query querySubject = book_db.whereEqualTo("subject", searchString);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document :task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("title").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("author").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("subject").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("ISBN").toString());
                            }
                        }
                        else {
                            Log.d(TAG, "no book found", task.getException());
                        }
                    }
                });
                querySubject.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document :task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("title").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("author").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("subject").toString());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("ISBN").toString());
                            }
                        }
                        else {
                            Log.d(TAG, "no book found", task.getException());
                        }
                    }
                });
                Log.d(TAG, "onClick: " + searchString);
            }
        });

    }
}
