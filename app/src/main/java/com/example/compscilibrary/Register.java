package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    //Connection to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_db =
            db.collection("CompSci_Db")
                    .document("Users").collection("all_users");

    //keys
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordInputConfirm;
    private Button confirmRegistrationButton;
    private static final String TAG = "activity_register";
    private String userInputEmail;
    private String userPasswordInput;
    private String userPasswordConfirmInput;
    private boolean alreadyMember;

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
                userInputEmail = emailInput.getText().toString();
                userPasswordInput = passwordInput.getText().toString();
                userPasswordConfirmInput = passwordInputConfirm.getText().toString();

                /* check if valid email entered before query */

                if (isValidEmail(userInputEmail) && userPasswordInput.equals(userPasswordConfirmInput)){
                    /*
                check if user already registered
                 query the email
                */
                    Query query = users_db.whereEqualTo("email", userInputEmail);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document :task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    alreadyMember=true;
                                }
                            }
                            else {
                                Log.d(TAG, "no user found: ", task.getException());
                                alreadyMember=false;
                            }
                        }
                    });

                    //if not in db add and send to login activity or if already member send to login
                    if (!alreadyMember){
                        //add to map
                        Map<String, Object> map = new HashMap<>();
                        map.put(KEY_EMAIL, userInputEmail);
                        map.put(KEY_PASSWORD, userPasswordInput);

                        //update db with map of details
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
                    else{
                        Toast.makeText(Register.this, "You have already registered please log in", Toast.LENGTH_LONG).show();
                        startActivity(loggedInIntent);
                    }
                }
                else if(!userPasswordInput.equals(userPasswordConfirmInput)){
                    Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Register.this, "Please enter a valid email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //check if input is an email
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}