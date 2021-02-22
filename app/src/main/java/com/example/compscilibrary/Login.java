package com.example.compscilibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity {

    //Connection to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users_db =
            db.collection("CompSci_Db")
                    .document("Users").collection("all_users");

    //keys
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    private static final String TAG = "activity_login";
    public Button loginSubmit;
    public EditText loginEmailInput;
    public EditText loginPasswordInput;
    public static final String ID ="id";
    public boolean registeredUser =false;
    public boolean wrongPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginSubmit = findViewById(R.id.login_confirm_button);
        loginEmailInput = findViewById(R.id.login_Email_Edit_Text);
        loginPasswordInput = findViewById(R.id.login_password_edit_text);

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userInputEmail = loginEmailInput.getText().toString();
                String userInputPassword = loginPasswordInput.getText().toString();
                //check if email valid
                loginAttempt(userInputEmail, userInputPassword);


            }
        });
    }


    //method to check if input is an email
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //login validation
    private void loginAttempt(String emailAttempt, String passwordAttempt){
        if (isValidEmail(emailAttempt)){

            //query db for user
            Query query = users_db.whereEqualTo("email", emailAttempt);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String password = (String) document.get(KEY_PASSWORD);
                            String email = (String) document.get(KEY_EMAIL);
                            String id = (String) document.getId();
                            if (passwordAttempt.equals(password)){
                                registeredUser = true;
                                Intent homeScreenIntent = new Intent(getApplicationContext(), HomeScreen.class);
                                homeScreenIntent.putExtra("user email", email);
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", email);
                                editor.putString("id", id);
                                editor.apply();
                                startActivity(homeScreenIntent);
                            }
                            else {
                                Toast.makeText(Login.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                                wrongPassword = true;
                            }
                        }
                    }
                    //if query unsuccessful
                    if (!registeredUser && !wrongPassword){
                        Toast.makeText(Login.this, "Account not found, please register", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(Login.this, Register.class);
                        startActivity(registerIntent);
                    }
                }
            });
        }
        else {
            Toast.makeText(Login.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }

    }
}