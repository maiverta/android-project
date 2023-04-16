package com.example.android_project.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.os.HandlerCompat;

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
    AppLocalDbRepository localDb=AppLocalDb.getAppDb();


    public static Model instance(){
        return _instance;
    }

    // no on can cretae multiple instances it private
    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface  GetAllObjectsListener{
        void onComplete(List<ObjectItem> data);
    }
    public interface GetAllPostsListener{
        void onComplete(List<Post> data);
    }
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public interface GetAllObjectsItemsListener{
        void onComplete(List<ObjectItem> data);
    }

    public interface  GetObjectListener{
        void onComplete(ObjectItem data);
    }
    public interface GetCitiesListener{
        void onComplete(String[] data);
    }

    public interface  AddObjectsListener{
        void onComplete();
    }

    public interface SignInListener{
        void onComplete(boolean data);
    }

    public void refreshAllObjects() {
    }

    public void getAllOtherObjects(GetAllObjectsListener callback){
        executor.execute(()->{
             List<ObjectItem> data = (List<ObjectItem>) localDb.objectItemDao().getAllOthers("mai");
            Log.d("ggg", "bbb" + data);

            mainHandler.post(()->{
                    callback.onComplete(data);
            });
        });
    }
    public void getMyObjects(GetAllObjectsListener callback){
        executor.execute(()->{
            List<ObjectItem> data = (List<ObjectItem>) localDb.objectItemDao().getMyObjects("mai");
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }
    public void addObject(ObjectItem objectItem, AddObjectsListener listener){
        executor.execute(()->{
            localDb.objectItemDao().insertAll(objectItem);
            mainHandler.post(()->{
                listener.onComplete();
            });
        });    }
    public void getObjectById(String id, GetObjectListener listener){
        executor.execute(()->{
            ObjectItem objectItem = localDb.objectItemDao().getById(id);
            Log.d("q", "ggg" + objectItem);
            mainHandler.post(()->{
                listener.onComplete(objectItem);
            });
        });
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


}
