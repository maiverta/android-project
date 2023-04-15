package com.example.android_project.ui.uploads;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.android_project.R;
import com.example.android_project.model.Model;
import com.example.android_project.model.ObjectItem;

import java.util.UUID;

public class EditObjectFragment extends Fragment {

    ObjectItem objectItem = new ObjectItem();
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

    CheckBox checkBox;

    TextView imageUrlTv;
    String imageUrl;

    TextView usernameTv;
    String username = "mai";

    String userPhonenumber= "0522735536";

    String objectId = "";

    Boolean isChecked = false;

    Spinner spinner;

    ArrayAdapter<String>adapter;

    String[] cities = new String[0];

    public static EditObjectFragment newInstance(String objectId){
        EditObjectFragment frag = new EditObjectFragment();
        Bundle bundle = new Bundle();
        Log.d("TAG", "ff444f "+ objectId);
        bundle.putString("ID",objectId);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            objectId = bundle.getString("objectId");

        }

    }

    public void setObjectValues(){
        for(int i = 0; i< cities.length; i++){
            if(objectItem.city.equals(cities[i])) {
                Log.d("ttt", i +"");
                spinner.setSelection(i);
            }
        }
//        imageUrlTv.setText(objectItem.getImageUrl());
        nameTv.setText(objectItem.getName());
        descriptionTv.setText(objectItem.getDescription());
        handTv.setText(objectItem.getHand().toString());
    notesTv.setText(objectItem.getNotes());
        checkBox.setChecked(objectItem.isTaken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_object, container, false);

//        imageUrlTv = view.findViewById(R.id.add_object_image);

        Model.instance().getCities((citiesToAdd)->{
            cities = citiesToAdd;
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,citiesToAdd);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();

            spinner = (Spinner) view.findViewById(R.id.edit_object_spinner);
            spinner.setAdapter(adapter);

        });
        nameTv = view.findViewById(R.id.edit_object_nameEt);
        handTv = view.findViewById(R.id.edit_object_handEt);
        descriptionTv= view.findViewById(R.id.edit_object_descriptionEt);
        notesTv= view.findViewById(R.id.edit_object_notesEt);
        Button button = view.findViewById(R.id.edit_object_saveBtn);

        checkBox= view.findViewById(R.id.edit_object_checkbox_isTaken);

        Model.instance().getObjectById(objectId, (ob)->{
            this.objectItem = ob;
            setObjectValues();
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->{
            this.isChecked = isChecked;
                Log.d("CHECKBOXES", "Cheese is checked: $isChecked" + isChecked);
        });
        button.setOnClickListener((view1)->{
            name = nameTv.getText().toString();
            notes = notesTv.getText().toString();
            imageUrl = "";
            hand = Integer.parseInt(handTv.getText().toString());
            description = descriptionTv.getText().toString();
            city = spinner.getSelectedItem().toString();
            ObjectItem updatedObjectItem = new ObjectItem(this.objectItem.id, name, hand, city, imageUrl, username, userPhonenumber, description, notes);
            updatedObjectItem.setTaken(isChecked);
            Model.instance().addObject(updatedObjectItem, ()->{
                Navigation.findNavController(view1).popBackStack();
            });
        });
        return view;
    }

}