package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText nameEt = findViewById(R.id.signup_nameEt);
        EditText emailEt = findViewById(R.id.signup_emailEt);
        EditText passwordEt = findViewById(R.id.signup_passwordEt);
        EditText cityEt = findViewById(R.id.signup_cityEt);
        EditText phoneNumberEt = findViewById(R.id.signup_phoneNumEt);

        Button signUpBtn = findViewById((R.id.saveBtn));
        Button loginBtn = findViewById((R.id.signup_login));


        signUpBtn.setOnClickListener(view1 -> {
            String name= nameEt.getText().toString();
        });

        loginBtn.setOnClickListener(view1 -> {
            Intent intent
                    = new Intent(SignUpActivity.this,
                    LoginActivity.class);
            startActivity(intent);        });
    }
}