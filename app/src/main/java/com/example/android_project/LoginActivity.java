package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.android_project.model.Model;
import com.example.android_project.model.Post;

public class LoginActivity extends AppCompatActivity {
    private EditText emailTV, passwordTV;
    private Button loginBtn, singUpBtn;
    private FirebaseAuth userAuth;

    @Override
    protected void onStart() {
        super.onStart();
        //Log out need to enable it on my profile page
        //        Model.instance().signout();
        FirebaseUser user = Model.instance().getcurrent();
        if (user !=null){
            Intent intent
                    = new Intent(LoginActivity.this,
                    Main2Activity.class);
            startActivity(intent);
        }
        Post.setLocalLastUpdate(new Long(0));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        loginBtn.setOnClickListener(view1->{
//            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
//            startActivity(intent);        });

        ///
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userAuth = FirebaseAuth.getInstance();

        emailTV = findViewById(R.id.login_emailEt);
        passwordTV = findViewById(R.id.login_passwordEt);
        loginBtn = findViewById(R.id.login_loginBtn);
        singUpBtn = findViewById(R.id.login_signupBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                login();
                Log.d("TAG", "The current user is:");
                Log.d("TAG", String.valueOf(Model.instance().getcurrent()));
            }
        });
        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(LoginActivity.this,
                        SignUpActivity.class);
                startActivity(intent);
            }
        });



    }

    private void login() {
        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        // validate email not empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Enter email",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // validate password not empty
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Enter password",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        Model.instance().signIn(email,password,(ok)->{
            if(ok){
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "failed to log in", Toast.LENGTH_LONG).show();
            }
        });


//        Toast.makeText(getApplicationContext(), "IsLoggedIn: "+managed_to_login, Toast.LENGTH_LONG).show();

//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(
//                        new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(
//                                    @NonNull Task<AuthResult> task)
//                            {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(getApplicationContext(),
//                                                    "Login successful!",
//                                                    Toast.LENGTH_LONG)
//                                            .show();
//
//                                    // if successful - go to main activity
//                                    Intent intent
//                                            = new Intent(LoginActivity.this,
//                                            MainActivity.class);
//                                    startActivity(intent);
//                                }
//                                else {
//                                    // sign-in failed

//                                }
//                            }
//                        });
    }
}