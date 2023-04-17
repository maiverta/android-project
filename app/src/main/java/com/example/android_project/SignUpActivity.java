package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android_project.model.Model;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    Button signUpBtn,loginBtn;
    ImageView IVPreviewImage;
    ImageButton uploadImageBtn;
    Intent signinInent;
    int REQUEST_CODE=1;
    private FirebaseAuth userAuth;
    int SELECT_PICTURE = 200;
    private static final int pic_id = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginBtn = findViewById(R.id.signup_loginBtn);
        // Initialize Firebase Auth
        userAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(SignUpActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        IVPreviewImage.setDrawingCacheEnabled(true);
        IVPreviewImage.buildDrawingCache();
        uploadImageBtn = findViewById(R.id.singup_cameraBtn);
        uploadImageBtn.setOnClickListener(view -> {
            imageChooser();
        });
        signUpBtn = findViewById(R.id.signup_signUpBtn);
        signUpBtn.setOnClickListener(view -> {
            EditText editText = (EditText)findViewById(R.id.signup_nameEt);
            String name = editText.getText().toString();
            editText = (EditText)findViewById(R.id.signup_emailEt);
            String email = editText.getText().toString();
            editText = (EditText)findViewById(R.id.signup_passwordEt);
            String password = editText.getText().toString();

            // validations for all inputs
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getApplicationContext(),
                                "Please enter your name",
                                Toast.LENGTH_LONG)
                        .show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(),
                                "Please enter email",
                                Toast.LENGTH_LONG)
                        .show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(),
                                "Please enter password",
                                Toast.LENGTH_LONG)
                        .show();
                return;
            }


            Log.d("TAG", name);
            Log.d("TAG", email);
            Log.d("TAG", password);
            createAccount(email,password,name);
//            Bitmap bmap = IVPreviewImage.getDrawingCache();

        });
    }

    void imageChooser() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Start the activity with camera_intent, and request pic id
        startActivityForResult(camera_intent, pic_id);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            mImageUri = data.getData();
//            mSelectImage.setImageURI(mImageUri);
            // Set the image in imageview for display
            IVPreviewImage.setImageBitmap(photo);
        }
    }

    private void createAccount(String email, String password,String name) {
        Model.instance().register(email,password,name,IVPreviewImage,(ok)->{
            if(ok){
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Register failed!", Toast.LENGTH_LONG).show();
            }
        });
    }
}