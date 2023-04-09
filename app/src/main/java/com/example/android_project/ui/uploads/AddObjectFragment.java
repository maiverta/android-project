package com.example.android_project.ui.uploads;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.android_project.R;
import com.example.android_project.model.Model;
import com.example.android_project.model.ObjectItem;

import java.util.UUID;

public class AddObjectFragment extends Fragment {
    TextView nameTv;
    String name;

    TextView descriptionTv;
    String description;


    TextView handTv;
    Integer hand;


    TextView cityTv;
    String city;

    TextView notesTv;
    String notes;

    TextView imageUrlTv;
    String imageUrl;

    TextView usernameTv;
    String username = "mai";

    TextView userPhonenumberTv;
    String userPhonenumber= "0522735536";

    public static AddObjectFragment newInstance(String title){
        AddObjectFragment frag = new AddObjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_object, container, false);

//        imageUrlTv = view.findViewById(R.id.add_object_image);

        nameTv = view.findViewById(R.id.add_object_nameEt);
        handTv = view.findViewById(R.id.add_object_handEt);
        cityTv = view.findViewById(R.id.add_object_cityEt);
        descriptionTv= view.findViewById(R.id.add_object_descriptionEt);
        notesTv= view.findViewById(R.id.add_object_notesEt);
        Button button = view.findViewById(R.id.add_object_saveBtn);
        button.setOnClickListener((view1)->{
            name = nameTv.getText().toString();
            notes = notesTv.getText().toString();
            imageUrl = "";
            hand = Integer.parseInt(handTv.getText().toString());
            description = descriptionTv.getText().toString();
            city = cityTv.getText().toString();
            ObjectItem objectItem = new ObjectItem(UUID.randomUUID().toString(), name, hand, city, imageUrl, username, userPhonenumber, description, notes);
            Model.instance().addObject(objectItem, ()->{
                Navigation.findNavController(view1).popBackStack();
            });
        });
        return view;
    }

}