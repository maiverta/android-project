package com.example.android_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_project.model.Model;
import com.example.android_project.model.Post;

import java.util.List;

public class MyPostsFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getMyPosts();

    public LiveData<List<Post>> getData() {
        return data;
    }

}