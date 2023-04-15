package com.example.android_project.ui.uploads;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android_project.ObjectListRecyclerAdapter;
import com.example.android_project.R;
import com.example.android_project.model.Model;
import com.example.android_project.model.ObjectItem;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadsFragment extends Fragment {
    List<ObjectItem> data = new LinkedList<>();
    com.example.android_project.ObjectListRecyclerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public UploadsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObjectListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadsFragment newInstance(String param1, String param2) {
        UploadsFragment fragment = new UploadsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_object_list, container, false);


        RecyclerView list = view.findViewById(R.id.objectRecycler_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        ObjectListRecyclerAdapter adapter = new ObjectListRecyclerAdapter(getLayoutInflater(),new LinkedList<>());
        list.setAdapter(adapter);
        Model.instance().getMyObjects((obList)->{
            data = obList;
            adapter.setData(data);
        });
        adapter.setOnItemClickListener(new ObjectListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) { // מה קורה שלוחצים על שורה

                Log.d("TAG", "ACTITY"+ pos);
                ObjectItem objectItem= data.get(pos);
                com.example.android_project.ui.uploads.UploadsFragmentDirections.ActionNavSlideshowToEditObjectFragment action = UploadsFragmentDirections.actionNavSlideshowToEditObjectFragment(objectItem.id);
                Navigation.findNavController(view).navigate(action);
            }
        });
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//                Log.d("TAG", "row was clieckes"+ pos);
//            }
//        });
        return view;
    }

   }