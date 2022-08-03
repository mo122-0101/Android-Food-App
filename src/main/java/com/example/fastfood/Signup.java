package com.example.fastfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class Signup extends AppCompatActivity {

    private static final String TAG = "SignUp";

    private ProgressDialog loader;

    //  Xml Hooks
    Button goSignIn;
    Button registration;
    TextInputLayout usernameLO; // LO for LayOut
    TextInputLayout phoneLO;
    TextInputLayout emailLO;
    TextInputLayout passwordLO;

    //  Firebase Hooks
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

//      go to signin page again
        goSignIn = findViewById(R.id.moveToSignInButton);
        goSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });

        registration = findViewById(R.id.regBtn);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, memu.class);
                startActivity(intent);
            }
        });
        
//      hooks for textInputLayout
        usernameLO = findViewById(R.id.username);
        phoneLO = findViewById(R.id.phone);
        emailLO = findViewById(R.id.email);
        passwordLO = findViewById(R.id.password);

    }

    //  validate function for user input before storing it in firebase
    private boolean validateUser() {
        String val = usernameLO.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            usernameLO.setError("field cann't be empty");
            return false;
        } else if (val.length() >= 8) {
            usernameLO.setError("username is too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            usernameLO.setError("whitespace are not allowed");
            return false;
        } else {
            usernameLO.setError(null);
            usernameLO.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = phoneLO.getEditText().getText().toString();
        if (val.isEmpty()) {
            phoneLO.setError("field cann't be empty");
            return false;
        } else if (val.length() > 11) {
            phoneLO.setError("number is too long");
            return false;
        } else {
            phoneLO.setError(null);
            phoneLO.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailLO.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            emailLO.setError("field cann't be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailLO.setError("Invalid email address");
            return false;
        } else {
            emailLO.setError(null);
            emailLO.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordLO.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            passwordLO.setError("field cann't be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwordLO.setError("password is too weak");
            return false;
        } else {
            passwordLO.setError(null);
            passwordLO.setErrorEnabled(false);
            return true;
        }
    }

    //  store data into firebase when click on sigup button
    public void registerMe(View view) {
        if (!validateUser() | !validatePhone() | !validateEmail() | !validatePassword()) { // any one return false get into if statement
            return;
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        String username = usernameLO.getEditText().getText().toString();
        String phone = phoneLO.getEditText().getText().toString();
        String email = emailLO.getEditText().getText().toString();
        String password = passwordLO.getEditText().getText().toString();

        UsersDataHelpers usersDataHelpers = new UsersDataHelpers(username, phone, email, password);
        databaseReference.child(username).setValue(usersDataHelpers);
    }
}