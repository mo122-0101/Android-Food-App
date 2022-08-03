package com.example.fastfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Food_order extends AppCompatActivity {
    TextInputLayout address; // LO for LayOut
    TextInputLayout choice;
    TextInputLayout req_number;
    Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);

        address=findViewById(R.id.address);
        choice=findViewById(R.id.choice);
        choice=findViewById(R.id.req_number);
        btnOk=findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}