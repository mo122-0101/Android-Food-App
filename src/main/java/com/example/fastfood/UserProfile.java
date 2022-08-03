package com.example.fastfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserProfile extends AppCompatActivity {
    TextInputLayout username, phone, email, password;
    TextView userprofilename;
Button resturantMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.usernameuserprofile);
        phone = findViewById(R.id.phoneuserprofile);
        email = findViewById(R.id.emailuserprofile);
        password = findViewById(R.id.passworduserprofile);
        userprofilename = findViewById(R.id.userprofilefullname);
        resturantMenu = findViewById(R.id.menu);

        resturantMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, memu.class);
                startActivity(intent);
            }
        });
        setUserProfileValues();
    }

    private void setUserProfileValues() {
        Intent intent = getIntent();
        String usernameFromSignIn = intent.getStringExtra("username");
        String phoneFromSignIn = intent.getStringExtra("phone");
        String emailFromSignIn = intent.getStringExtra("email");
        String passwordFromSignIn = intent.getStringExtra("password");

        username.getEditText().setText(usernameFromSignIn);
        phone.getEditText().setText(phoneFromSignIn);
        email.getEditText().setText(emailFromSignIn);
        password.getEditText().setText(passwordFromSignIn);

        userprofilename.setText(usernameFromSignIn);
    }
}