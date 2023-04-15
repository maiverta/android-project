package com.example.finalproject.model;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.core.os.HandlerCompat;

import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    private FirebaseStoreageModel firebaseStoreageModel = new FirebaseStoreageModel();
    private Firestore firestore = new Firestore();
    private FirebaseUserModel userModel = new FirebaseUserModel();
    AppLocalDbRepository localDb=AppLocalDb.getAppDb();


    public static Model instance(){

        return _instance;
    }
    private Model(){
    }

//    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public interface GetAllPostsListener{
        void onComplete(List<Post> data);
    }

//    public void getAllUsers(GetAllUsersListener callback){
//
//        firebaseModel.getAllUsers(callback);
//    }

//    public interface AddUserListener{
//        void onComplete();
//    }
//
//    public void addUser(User user, AddUserListener listener){
//        firebaseModel.addUser(user,listener);
//    }
    public interface SignInListener{
        void onComplete(boolean data);
    }
    public void signIn(String email, String password,SignInListener callback){
        userModel.signIn(email,password,callback);
    }
    public void getBitMap(String path, ImageView img) {

        firebaseStoreageModel.getImage(path,img);
    }
    public FirebaseUser getcurrent(){

        return userModel.getUser();
    }
    public void signout(){

        userModel.signout();
    }
    public void uploadImage(String name, byte[] data, Listener<String> listener) {
        firebaseStoreageModel.uploadImage(name,data,listener);
    }

    public void refreshAllPosts()
    {
        //get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        //get all  posts since
        firestore.getAllPostsSince(localLastUpdate,list->{
            executor.execute(()->{
                Long time = localLastUpdate;
                for(Post ps:list){
                    //insert new records into ROOM
                    localDb.postDao().insertAll(ps);
                    if(time<ps.getLastUpdated()){
                        time=ps.getLastUpdated();
                    }
                }
                // update local last update
                Post.setLocalLastUpdate(time);
            });

        });
    }










}



