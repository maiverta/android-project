package com.example.android_project;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
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

import com.example.android_project.PostsListFragmentDirections;
import com.example.android_project.databinding.FragmentMyPostsBinding;
import com.example.android_project.databinding.FragmentPostListBinding;
import com.example.android_project.model.Model;
import com.example.android_project.model.Post;
//import com.example.android_project.ui.uploads.UploadsFragmentDirections;

import java.util.LinkedList;
import java.util.List;

public class MyPostsFragment extends Fragment {

    List<Post> data = new LinkedList<>();
    PostsRecyclerAdapter adapter;
    MyPostsFragmentViewModel postListViewModel;
    FragmentMyPostsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        }, this, Lifecycle.State.RESUMED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPostsBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        adapter = new PostsRecyclerAdapter(getLayoutInflater(), postListViewModel.getData().getValue(), false);

        binding.myObjectRecyclerList.setHasFixedSize(true);

        binding.myObjectRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.myObjectRecyclerList.setAdapter(adapter);


        postListViewModel.getData().observe(getViewLifecycleOwner(), (list1) -> {
            data = list1;
            adapter.setData(list1);
        });

//        Model.instance().getAllOtherObjects((obList)->{
//            data = obList;
//            adapter.setData(data);
//        });

        adapter.setOnPostClickListener(new PostsRecyclerAdapter.OnPostClickListener() {
            @Override
            public void onItemClick(int pos) { // מה קורה שלוחצים על שורה
                Log.d("TAG", "ACTITY"+ pos);
                Post post= data.get(pos);
                MyPostsFragmentDirections.ActionMyPostsFragmentToEditPostFragment action= MyPostsFragmentDirections.actionMyPostsFragmentToEditPostFragment(post.id);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllPosts();
        binding.progressBar.setVisibility(View.GONE);

    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        postListViewModel = new ViewModelProvider(this).get(MyPostsFragmentViewModel.class);
    }
}

