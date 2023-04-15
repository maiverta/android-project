package com.example.finalproject.model;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;

public class FirebaseUserModel {
    private FirebaseAuth users = FirebaseAuth.getInstance();
    FirebaseUser currentuser;
    public FirebaseUser getUser (){
        currentuser = users.getCurrentUser();
        return currentuser;
    }
    public void signout(){
//        FirebaseAuth.getInstance().signOut();
        users.signOut();
    }

    public void signIn(String email, String password, Model.SignInListener callback) {
        users.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            boolean success;
                            if (task.isSuccessful()) {
                                success = true;
                                Log.d("TAG","Success!");
                            }
                            else {
                                success=false;
                                Log.d("TAG","Failure!");
                            }
                            callback.onComplete(success);
                        });
    }

    public void register(String email, String password,String firstname,String lastname,ImageView IVPreviewImage, Model.SignInListener callback) {

        users.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean success;
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            success = true;
                            Bitmap bmap = ((BitmapDrawable) IVPreviewImage.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();
                            Model.instance().uploadImage(Model.instance().getcurrent().getUid()+"_",data,url->Log.d("TAG","Start to upload"));

                            FirebaseUser user =Model.instance().getcurrent();
                            UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder().setDisplayName(firstname).build();
                            user.updateProfile(profileupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        String displayName =Model.instance().getcurrent().getDisplayName();
                                        Log.d("TAG","User profile updated");
                                        Log.d("TAG","Display name: "+ displayName);


                                    } else {
                                        Log.d("TAG","User profile was not updated");
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            success = false;
                        }

                        callback.onComplete(success);
                    }
                });
        // [END create_user_with_email]
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(
//                        task -> {
//                            boolean success;
//                            if (task.isSuccessful()) {
//                                success = true;
//                                Log.d("TAG","Success!");
//                            }
//                            else {
//                                success=false;
//                                Log.d("TAG","Failure!");
//                            }
//                            callback.onComplete(success);
//                        });
    }
}
