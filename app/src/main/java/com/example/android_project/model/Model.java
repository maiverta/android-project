package com.example.android_project.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.firebase.auth.FirebaseUser;

public class Model {

    // create it just one instance - singletone
    private static final Model _instance = new Model();
    // Create new thread for async
    private Executor executor = Executors.newSingleThreadExecutor();
    // return to main thread
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    private FirebaseStoreageModel firebaseStoreageModel = new FirebaseStoreageModel();
    private Firestore firestore = new Firestore();
    private FirebaseUserModel userModel = new FirebaseUserModel();
    private LiveData<List<Post>> postsList;

    private LiveData<List<Post>> myPostsList;
    AppLocalDbRepository localDb=AppLocalDb.getAppDb();
    List<Post> data = new LinkedList<>();


    public static Model instance(){
        return _instance;
    }

    // no on can cretae multiple instances it private
    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public interface GetAllPostsListener{
        void onComplete(List<Post> data);
    }

    public interface  GetPostListener{
        void onComplete(Post data);
    }

    public interface SignInListener{
        void onComplete(boolean data);
    }

    public interface GetCitiesListener{
        void onComplete(String[] data);
    }

    public void getCities(GetCitiesListener listener){
        LinkedList<String> citiesToAdd = new LinkedList<>();

        executor.execute(()->{
                    try {
                        // GET /v1/geo/countries/IL/regions?limit=5&offset=0

                        String queryString = "http://geodb-free-service.wirefreethought.com/v1/geo/cities/54158/nearbyCities?radius=100&hateoasMode=false&limit=10&offset=0";
                        HttpURLConnection connection = null;
                        try {
                            connection = (HttpURLConnection)new URL(queryString).openConnection();
                            connection.setRequestMethod("GET");

                            InputStream inputStream = connection.getInputStream();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();



                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray citiesWithExtraData = jsonObject.getJSONArray("data");


                            for (int i = 0; i < citiesWithExtraData.length(); i++) {
                                citiesToAdd.add(citiesWithExtraData.getJSONObject(i).getString("city"));
                            }


//                        String currency_response = jsonObject.getJSONObject("exchange_rates").get("city").toString();
                            Log.d("TAG", citiesToAdd.toString());

//                        Double price_usd = Double.parseDouble(currency_response)*20;


                        } catch (IOException e) {
                            System.out.println(e);

                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                                mainHandler.post(()->{
                                    listener.onComplete(citiesToAdd.toArray(new String[0]));
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



        });
    }

    public void signIn(String email, String password,SignInListener callback){
        userModel.signIn(email,password,callback);
    }
    public void signout(){
        userModel.signout();
    }
    public void register(String email, String password,String name,ImageView IVPreviewImage, SignInListener callback){
        userModel.register(email,password,name,IVPreviewImage,callback);
    }

    public void getBitMap(String path, ImageView img) {

        firebaseStoreageModel.getImage(path,img);
    }

    public FirebaseUser getcurrent(){
        return userModel.getUser();
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
    public LiveData<List<Post>> getAllPosts(){
        if(postsList ==null){
            postsList = localDb.postDao().getAll(getcurrent().getEmail());
        }
        return postsList;

    }

    public LiveData<List<Post>> getMyPosts(){
        if(myPostsList ==null){
            Log.d("gggg", getcurrent().getEmail());
            myPostsList = localDb.postDao().getUsersPosts(getcurrent().getEmail());
        }
        return myPostsList;

    }

    public void getPostById(String id, GetPostListener listener){
        executor.execute(()->{
            Post post = localDb.postDao().getById(id);
            Log.d("q", "ggg" + post);
            mainHandler.post(()->{
                listener.onComplete(post);
            });
        });
    }

    public void addPost(Post post)
    {
        data.add(post);
    }

    public void getPostEdit (String postId, EditText et_title, EditText et_desc, EditText et_hand,
                             EditText et_phoneNumber, EditText et_notes, ImageView imgView, CheckBox isTakenCB, Spinner citySpinner,String[] cities){
        firestore.getPostEdit(postId, et_title, et_desc, et_hand,et_phoneNumber,et_notes, imgView, isTakenCB, citySpinner, cities);
    }

    public void getPostView(String postId, TextView et_title, TextView et_desc, TextView et_hand, TextView et_email,
                            TextView et_city, TextView et_notes, ImageView imgView){
        firestore.getPostView(postId, et_title, et_desc,et_email, et_hand, et_city,et_notes, imgView);
    }

}
