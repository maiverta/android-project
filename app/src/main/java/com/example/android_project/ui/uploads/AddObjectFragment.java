package com.example.android_project.ui.uploads;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.android_project.Main2Activity;
import com.example.android_project.R;
import com.example.android_project.model.Model;
import com.example.android_project.model.ObjectItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

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

    Spinner spinner;

    ArrayAdapter<String>adapter;


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
        //get the spinner from the xml.


        View view = inflater.inflate(R.layout.fragment_add_object, container, false);

        Model.instance().getCities((citiesToAdd)->{
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,citiesToAdd);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();

            spinner = (Spinner) view.findViewById(R.id.object_spinner);
            spinner.setAdapter(adapter);

        });







//        imageUrlTv = view.findViewById(R.id.add_object_image);

        nameTv = view.findViewById(R.id.add_object_nameEt);
        handTv = view.findViewById(R.id.add_object_handEt);
        descriptionTv= view.findViewById(R.id.add_object_descriptionEt);
        notesTv= view.findViewById(R.id.add_object_notesEt);
        Button button = view.findViewById(R.id.add_object_saveBtn);

        button.setOnClickListener((view1)->{
            city = spinner.getSelectedItem().toString();
            name = nameTv.getText().toString();
            notes = notesTv.getText().toString();
            imageUrl = "";
            hand = Integer.parseInt(handTv.getText().toString());
            description = descriptionTv.getText().toString();
            ObjectItem objectItem = new ObjectItem(UUID.randomUUID().toString(), name, hand, city, imageUrl, username, userPhonenumber, description, notes);
            Model.instance().addObject(objectItem, ()->{
                Navigation.findNavController(view1).popBackStack();
            });
        });
        return view;
    }





}