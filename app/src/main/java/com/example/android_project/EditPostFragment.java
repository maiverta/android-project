package com.example.android_project;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.android_project.model.Model;
import com.example.android_project.model.Post;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class EditPostFragment extends Fragment {

    int SELECT_PICTURE = 200;
    ImageView IVPreviewImage;
    String post_id;
    Spinner citySpinner;
    ArrayAdapter<String>adapter;

    String[] cities = new String[0];

    EditText titleTv;
    EditText descriptionTv;
    EditText notesTv;
    EditText phoneTv;
    CheckBox isTakenCB;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post_id = getArguments().getString("PostId");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);


        EditText titleTv = view.findViewById(R.id.edit_post_titleEt);
        EditText handTv = view.findViewById(R.id.edit_post_handEt);
        EditText descriptionTv= view.findViewById(R.id.edit_post_descriptionEt);
        EditText notesTv= view.findViewById(R.id.edit_post_notesEt);
        EditText phoneTv = view.findViewById(R.id.edit_post_phone);
        CheckBox isTakenCB =  view.findViewById(R.id.edit_post_checkbox_isTaken);

        IVPreviewImage = view.findViewById(R.id.edit_post_image);

        //        edit_post_spinner
        Model.instance().getCities((citiesToAdd)->{
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,citiesToAdd);
            cities = citiesToAdd;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();

            citySpinner = (Spinner) view.findViewById(R.id.edit_post_spinner);
            citySpinner.setAdapter(adapter);

            Model.instance().getPostEdit(post_id, titleTv, descriptionTv, handTv,phoneTv,notesTv, IVPreviewImage, isTakenCB, citySpinner, cities);


        });



        Button saveBtn = view.findViewById(R.id.edit_post_saveBtn);
//        Button add_image = view.findViewById(R.id.edit_cameraBtn);

//        add_image.setOnClickListener(view1->{
//            image_chooser();
//        });

//        isTakenCB.setOnCheckedChangeListener((buttonView, isChecked) ->{
//            this.isTaken = isChecked;
//            Log.d("CHECKBOXES", "Cheese is checked: $isChecked" + isChecked);
//        });

        saveBtn.setOnClickListener(view1 -> {
            String title = titleTv.getText().toString();
            String desc = descriptionTv.getText().toString();
            Integer hand = Integer.parseInt(handTv.getText().toString());
            String phone = phoneTv.getText().toString();
            String city = citySpinner.getSelectedItem().toString();
            String notes = notesTv.getText().toString();
            Boolean isTaken = isTakenCB.isChecked();
            Log.d("rrrrrrr", isTaken.toString());

            String email = Model.instance().getcurrent().getEmail();
            String imagePath = post_id+"_"+title+"_"+email.split("@")[0];

            Post.addPost(post_id, title, desc,hand, city,email,phone,isTaken,notes,imagePath);

            Bitmap bmap = ((BitmapDrawable) IVPreviewImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            Model.instance().uploadImage(imagePath,data,url->Log.d("TAG","Start to upload"));
            Navigation.findNavController(view1).popBackStack();

            Toast.makeText(getContext(),
                            "Update post successfully",
                            Toast.LENGTH_LONG)
                    .show();

        });
        return view;

    }


    public void setObjectValues(){
//        for(int i = 0; i< cities.length; i++){
//            if(objectItem.city.equals(cities[i])) {
//                Log.d("ttt", i +"");
//                spinner.setSelection(i);
//            }
//        }

    }
    private void image_chooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}