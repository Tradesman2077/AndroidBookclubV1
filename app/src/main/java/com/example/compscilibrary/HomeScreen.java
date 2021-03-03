package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference news_db = db.collection("CompSci_Db")
            .document("news").collection("changes");


    private static final String TAG = "HomeScreen";
    private TextView userNameText;
    private Button searchButton;
    private Button addBookButton;
    private Button allBooksButton;
    private LinearLayout newsList;

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
        newsList = findViewById(R.id.newsLayout);

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

        getAllNews();
    }

    //method that returns all reviews left recently and displays at bottom of home screen
    private void getAllNews(){
        news_db.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    Map<String, Object> data = snapshots.getData();
                    for (String i : data.keySet()) {
                        Log.d(TAG, "onSuccess: " + "key: " + i + " value: " + data.get(i));
                        TextView newTextView = new TextView(getApplicationContext());
                        newTextView.setText("\n" +i + " -" + data.get(i)+"\n");

                        CardView newCardView = new CardView(getApplicationContext());
                        newCardView.setUseCompatPadding(true);
                        newCardView.setCardElevation(6);
                        newCardView.setPadding(6,6,6,6);
                        newCardView.addView(newTextView);
                        newsList.addView(newCardView);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " +e);
            }
        });
    }

}