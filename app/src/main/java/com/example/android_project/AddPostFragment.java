package com.example.android_project;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import com.example.android_project.model.Firestore;
import com.example.android_project.model.Model;
import com.google.firebase.firestore.DocumentReference;

public class AddPostFragment extends Fragment {
    int SELECT_PICTURE = 200;
    ImageView IVPreviewImage;
    Spinner citySpinner;
    ArrayAdapter<String>adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addPostFragment);

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);


    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        Model.instance().getCities((citiesToAdd)->{
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,citiesToAdd);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();

            citySpinner = (Spinner) view.findViewById(R.id.add_post_city);
            citySpinner.setAdapter(adapter);

        });
        IVPreviewImage = view.findViewById(R.id.add_post_image);

        EditText titleTv = view.findViewById(R.id.add_post_titleEt);
        EditText handTv = view.findViewById(R.id.add_post_handEt);
        EditText descriptionTv= view.findViewById(R.id.add_post_descriptionEt);
        EditText notesTv= view.findViewById(R.id.add_post_notesEt);
        EditText phoneTv = view.findViewById(R.id.add_post_phone);

//        EditText city = view.findViewById(R.id.add_post_city);
//        Button button = view.findViewById(R.id.add_post_saveBtn);

        Button saveBtn = view.findViewById(R.id.add_post_saveBtn);
        ImageButton add_image = view.findViewById(R.id.addPost_cameraBtn);
        //When clicked - move to list page
//        Button cancelBtn = view.findViewById(R.id.addpost_cancell_btn);
//        cancelBtn.setOnClickListener(view1->{
//            Navigation.findNavController(view1).popBackStack();
//        });
        add_image.setOnClickListener(view1->{
            image_chooser();
        });
        saveBtn.setOnClickListener(view1 -> {
            String title = titleTv.getText().toString();
            String desc = descriptionTv.getText().toString();
//            Integer price = Integer.parseInt(priceEt.getText().toString());
            String hand = handTv.getText().toString();
            String phone = phoneTv.getText().toString();
            String city = citySpinner.getSelectedItem().toString();
            String notes = notesTv.getText().toString();


            DocumentReference ref = Firestore.instance().getDb().collection("posts").document();
            String collection_id = ref.getId();


            Toast.makeText(getContext(),
                            "Upload post successfully",
                            Toast.LENGTH_LONG)
                    .show();
//            Navigation.findNavController(view1).popBackStack(R.id.m,false);
            Navigation.findNavController(view1).popBackStack();
        });

//        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.postsListFragment,false));

        return view;

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