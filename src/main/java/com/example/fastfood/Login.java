package com.example.fastfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";


    //  variables
    ImageView imageLogin;
    TextView textLogin, sloganLogin;
    TextInputLayout usernamelgoin;
    TextInputLayout passwordlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        imageLogin = findViewById(R.id.image_login);
        textLogin = findViewById(R.id.text_logo_login);
        sloganLogin = findViewById(R.id.slogan_login);

//      hooks for textInputLayout
        usernamelgoin = findViewById(R.id.usernamelgoin);
        passwordlogin = findViewById(R.id.passwordlogin);
    }

    //    go to sign up
    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getBaseContext(), Signup.class);
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(imageLogin, "imageTransition");
        pairs[1] = new Pair<View, String>(textLogin, "textTransition");
        pairs[2] = new Pair<View, String>(sloganLogin, "sloganTransition");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, activityOptions.toBundle());
    }

    //  validate function for user input before storing it in firebase
    private boolean validateUser() {
        String val = usernamelgoin.getEditText().getText().toString();
        if (val.isEmpty()) {
            usernamelgoin.setError("field cann't be empty");
            return false;
        } else {
            usernamelgoin.setError(null);
            usernamelgoin.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordlogin.getEditText().getText().toString();
        if (val.isEmpty()) {
            passwordlogin.setError("field cann't be empty");
            return false;
        } else {
            passwordlogin.setError(null);
            passwordlogin.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {
        final String usernameEnteredValue = usernamelgoin.getEditText().getText().toString().trim();
        final String passwordEnteredValue = passwordlogin.getEditText().getText().toString().trim();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = databaseReference.orderByChild("username").equalTo(usernameEnteredValue);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    usernamelgoin.setError(null);
                    usernamelgoin.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(usernameEnteredValue).child("password").getValue(String.class);

                    if (passwordFromDB.equals(passwordEnteredValue)) {

                        usernamelgoin.setError(null);
                        usernamelgoin.setErrorEnabled(false);

                        String usernameFromDB = snapshot.child(usernameEnteredValue).child("username").getValue(String.class);
                        String phoneFromDB = snapshot.child(usernameEnteredValue).child("phone").getValue(String.class);
                        String emailFromDB = snapshot.child(usernameEnteredValue).child("email").getValue(String.class);

                        Intent intent = new Intent(Login.this, UserProfile.class);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        passwordlogin.setError("wrong password");
                        passwordlogin.requestFocus();
                    }
                } else {
                    usernamelgoin.setError("no such username");
                    usernamelgoin.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //    go to user profile
    public void callUserProfileScreen(View view) {
        if (!validateUser() | !validatePassword()) {
            return;
        } else {
            Log.e(TAG, "callUserProfileScreen: is user method is below");
            isUser();
        }
    }

}