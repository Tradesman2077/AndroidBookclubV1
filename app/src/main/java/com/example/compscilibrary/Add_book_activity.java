package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_book_activity extends AppCompatActivity {

    //Connection to firestore
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference book_db =
            db.collection("CompSci_Db")
                    .document("books").collection("book_list");

    private EditText titleField;
    private EditText authorField;
    private EditText subjectField;
    private EditText isbnField;
    private Button submitAddBookButton;
    String authorInput;
    String titleInput;
    String isbnInput;
    String subjectInput;
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_ISBN = "ISBN";
    public static final String KEY_SUBJECT = "subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_activity);

        titleField = findViewById(R.id.title_text_field);
        authorField = findViewById(R.id.author_text_field);
        subjectField = findViewById(R.id.subject_text_field);
        isbnField = findViewById(R.id.isbn_text_field);
        submitAddBookButton = findViewById(R.id.add_book_submit_button);

        submitAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorInput = authorField.getText().toString();
                titleInput = titleField.getText().toString();
                isbnInput = isbnField.getText().toString();
                subjectInput = subjectField.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put(KEY_TITLE, titleInput);
                map.put(KEY_AUTHOR, authorInput);
                map.put(KEY_SUBJECT, subjectInput);
                map.put(KEY_ISBN, isbnInput);


                //update db with map of details
                book_db.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Add_book_activity.this, "You have successfully added the book", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_book_activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }
}