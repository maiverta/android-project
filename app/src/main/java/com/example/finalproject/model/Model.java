package com.example.finalproject.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();

    public static Model instance(){

        return _instance;
    }
    private Model(){
    }

//    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public void getAllUsers(GetAllUsersListener callback){

        firebaseModel.getAllUsers(callback);
    }

    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(User user, AddUserListener listener){
        firebaseModel.addUser(user,listener);
    }


}



