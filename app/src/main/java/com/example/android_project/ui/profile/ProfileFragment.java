package com.example.android_project.ui.profile;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android_project.LoginActivity;
import com.example.android_project.R;
import com.example.android_project.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    private EditText profile_name_et, email_et;
    private EditText passwordTextView, updatedPasswordTextView;
    FirebaseAuth userAuth;
    int SELECT_PICTURE = 200;
    ImageView IVPreviewImage;

    public ProfileFragment() {
        // Required empty public constructor
    }


//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();

    }

    private void updateUserPassword(String password, String newPass) {
        FirebaseUser user = Model.instance().getcurrent();
        final String email = user.getEmail();
        Log.d(TAG, "email");
        Log.d(TAG, email);
        Log.d(TAG, "Password");
        Log.d(TAG, password);
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User re-authenticated.");

                    user.updatePassword(newPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                        Toast.makeText(getContext(),
                                                        "User password updated.",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                    } else {
                                        Log.d("TAG", "User did not change password");
                                    }
                                }
                            });
                } else {
                    Log.d("TAG", "Did not e-authenticate");
                }
            }
        });

    }

    private void image_chooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @SuppressLint({"MissingInflatedId", "WrongThread"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        String displayName = Model.instance().getcurrent().getDisplayName();

        profile_name_et = view.findViewById(R.id.profile_nameEt);
        profile_name_et.setText(displayName);

        String email = Model.instance().getcurrent().getEmail();
        email_et = view.findViewById(R.id.profile_emailEt);
        email_et.setText(email);


        passwordTextView = view.findViewById(R.id.profile_passwordEt);
        updatedPasswordTextView = view.findViewById(R.id.profile_newPasswordEt);
//        EditText cityEt = view.findViewById(R.id.profile_cityEt);
//        EditText phoneNumberEt = view.findViewById(R.id.profile_phoneNumEt);

        IVPreviewImage = view.findViewById(R.id.profile_image);
        FirebaseStorage.getInstance().getReference().child("images/").child(Model.instance().getcurrent().getUid()+"_"+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(IVPreviewImage);
            }
        });

        Button signout = view.findViewById(R.id.profile_logoutBtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance().signout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        Button updatePass = view.findViewById(R.id.profile_change_pass_btn);

        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPass = passwordTextView.getText().toString();
                String newPass = updatedPasswordTextView.getText().toString();
                updateUserPassword(currentPass, newPass);
            }
        });

        return view;
    }
}