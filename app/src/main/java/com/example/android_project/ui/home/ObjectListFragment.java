package com.example.android_project.ui.home;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.android_project.ObjectListRecyclerAdapter;
import com.example.android_project.R;
import com.example.android_project.model.Model;
import com.example.android_project.model.ObjectItem;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectListFragment extends Fragment {
    List<ObjectItem> data = new LinkedList<>();
    ObjectListRecyclerAdapter adapter;

    public ObjectListFragment() {
        // Required empty public constructor
    }


    public static ObjectListFragment newInstance(String param1, String param2) {
        ObjectListFragment fragment = new ObjectListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.objectFragment);
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
        View view = inflater.inflate(R.layout.fragment_object_list, container, false);

        RecyclerView list = view.findViewById(R.id.objectRecycler_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ObjectListRecyclerAdapter(getLayoutInflater(),new LinkedList<>());
        list.setAdapter(adapter);

        Model.instance().getAllOtherObjects((obList)->{
            data = obList;
            adapter.setData(data);
        });

        adapter.setOnItemClickListener(new ObjectListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) { // מה קורה שלוחצים על שורה

                Log.d("TAG", "ACTITY"+ pos);
                ObjectItem objectItem= data.get(pos);
            ObjectListFragmentDirections.ActionNavHomeToObjectItemViewFragment action = ObjectListFragmentDirections.actionNavHomeToObjectItemViewFragment(objectItem.id);
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

    class ObjectViewHolder extends  RecyclerView.ViewHolder{
        TextView name;
        TextView hand;
        TextView city;
        public ObjectViewHolder(@NonNull View itemView, ObjectListRecyclerAdapter.OnItemClickListener listener) {
            super(itemView);
            name= itemView.findViewById(R.id.objectlistrow_name);
            hand= itemView.findViewById(R.id.objectlistrow_hand);
            city = itemView.findViewById(R.id.objectlistrow_Location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Log.d("TAG","row click"+ pos);
                    listener.onItemClick(pos);
                }
            });

        }

        // Bind data to design
        public void bind(ObjectItem objectItem){
            name.setText(objectItem.name);
            hand.setText(objectItem.hand + "");
            city.setText(objectItem.city);

        }
    }


}