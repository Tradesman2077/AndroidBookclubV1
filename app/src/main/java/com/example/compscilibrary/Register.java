package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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

public class Register extends AppCompatActivity {

    //Connection to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_db =
            db.collection("CompSci_Db")
                    .document("Users").collection("all_users");

    //keys
    public static final String KEY_TITLE = "email";
    public static final String DESCRIPTION = "password";
    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordInputConfirm;
    private Button confirmRegistrationButton;
    private static final String TAG = "activity_register";
    private String userInputEmail;
    private String userPasswordInput;
    private String userPasswordConfirmInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.email_entry_register);
        passwordInput = findViewById(R.id.password_entry_edit_text);
        passwordInputConfirm = findViewById(R.id.confirm_password_entry);
        confirmRegistrationButton = findViewById(R.id.submit_register_button);
        Intent loggedInIntent = new Intent(this, Login.class);


        confirmRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user data from fields
                userInputEmail = String.valueOf(emailInput.getText());
                userPasswordInput = String.valueOf(passwordInput.getText());
                userPasswordConfirmInput = String.valueOf(passwordInputConfirm.getText());

                //add to map
                Map<String, Object> map = new HashMap<>();
                map.put(KEY_TITLE, userInputEmail);
                map.put(DESCRIPTION, userPasswordInput);

                //update db with map
                users_db.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Register.this, "You have been successfully registered", Toast.LENGTH_LONG).show();
                        startActivity(loggedInIntent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

}