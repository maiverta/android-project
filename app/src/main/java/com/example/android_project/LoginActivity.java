package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        mAuth = FirebaseAuth.getInstance();
        EditText passwordEt = findViewById(R.id.login_passwordEt);
        EditText emailEt = findViewById(R.id.login_emailEt);


        Button signUpBtn = findViewById((R.id.login_signupBtn));
        Button loginBtn = findViewById((R.id.login_loginBtn));

        loginBtn.setOnClickListener(view1->{
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(intent);        });
        signUpBtn.setOnClickListener(view1 -> {
            Intent intent
                    = new Intent(LoginActivity.this,
                    SignUpActivity.class);
            startActivity(intent);        });



    }
}