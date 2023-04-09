package com.example.android_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_project.model.Model;
import com.example.android_project.model.ObjectItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObjectItemViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectItemViewFragment extends Fragment {

    ObjectItem objectItem = new ObjectItem();


    TextView nameTv;
    String name;

    TextView descriptionTv;
    String description;


    TextView handTv;
    Number hand;


    TextView cityTv;
    String city;

    TextView notesTv;
    String notes;

    TextView imageUrlTv;
    String imageUrl;

    TextView usernameTv;
    String username;

    TextView userPhonenumberTv;
    String userPhonenumber;

    private String objectId;


    public ObjectItemViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ObjectItemView.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectItemViewFragment newInstance(){
        ObjectItemViewFragment fragment = new ObjectItemViewFragment();
        Bundle args = new Bundle();
//        args.putString("ID", objectId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            objectId = bundle.getString("objectId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_object_item_view, container, false);
//
        nameTv = view.findViewById(R.id.object_item_view_name);
        handTv = view.findViewById(R.id.object_item_view_hand);
        descriptionTv= view.findViewById(R.id.object_item_view_description);
        notesTv= view.findViewById(R.id.object_item_view_notes);
        cityTv = view.findViewById(R.id.object_item_view_city);
        usernameTv = view.findViewById(R.id.object_item_view_username);
        userPhonenumberTv = view.findViewById(R.id.object_item_view_user_phonenumber);
        // imageUrlTv= view.findViewById(R.id.object_item_view_imageUrl);
//
//
        Model.instance().getObjectById(objectId, (ob)->{
            this.objectItem = ob;
            setObjectValues();
        });

        


        return view;
    }

    public void setObjectValues(){
        Log.d("nameeeeee", "name" + objectItem.name);
//        imageUrlTv.setText(objectItem.getImageUrl());
        nameTv.setText(objectItem.getName());
        descriptionTv.setText(objectItem.getDescription());
        handTv.setText(objectItem.getHand().toString());
        cityTv.setText(objectItem.getCity());
        notesTv.setText(objectItem.getNotes());
        usernameTv.setText(objectItem.getUsername());
        userPhonenumberTv.setText(objectItem.getPhoneNumber());
    }
}