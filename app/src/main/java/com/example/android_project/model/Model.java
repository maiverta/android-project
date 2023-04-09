package com.example.android_project.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    // create it just one instance - singletone
    private static final Model _instance = new Model();

    // Create new thread for async
    private Executor executor = Executors.newSingleThreadExecutor();

    // return to main thread
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    public interface Listener<T>{
        void onComplete(T data);
    }

    public interface GetAllObjectsItemsListener{
        void onComplete(List<ObjectItem> data);
    }

    public static Model instance(){
        return _instance;
    }

    // no on can cretae multiple instances it private
    private Model(){
//        for(int i=0; i< 20; i++){
//            addObject(new ObjectItem("name" + i,i%2, "Ashdod", "", new User("ma", "05227355445"), "Description" + i, "notesss" +i+ " notes") );
//        }
    }

    AppLocalDbRepository localDb =   AppLocalDb.getAppDb();

    public interface  GetAllObjectsListener{
        void onComplete(List<ObjectItem> data);
    }
    public void getAllOtherObjects(GetAllObjectsListener callback){
        executor.execute(()->{
             List<ObjectItem> data = localDb.objectItemDao().getAllOthers("mai");
            Log.d("ggg", "bbb" + data);

            mainHandler.post(()->{
                    callback.onComplete(data);
            });
        });
    }

    public void getMyObjects(GetAllObjectsListener callback){
        executor.execute(()->{
            List<ObjectItem> data = localDb.objectItemDao().getMyObjects("mai");
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public void refreshAllObjects()
    {


    }

    public interface  AddObjectsListener{
        void onComplete();
    }

    public void addObject(ObjectItem objectItem, AddObjectsListener listener){
        executor.execute(()->{
            localDb.objectItemDao().insertAll(objectItem);
            mainHandler.post(()->{
                listener.onComplete();
            });
        });    }


    public interface  GetObjectListener{
        void onComplete(ObjectItem data);
    }
    public void getObjectById(String id, GetObjectListener listener){
        executor.execute(()->{
            ObjectItem objectItem = localDb.objectItemDao().getById(id);
            Log.d("q", "ggg" + objectItem);
            mainHandler.post(()->{
                listener.onComplete(objectItem);
            });
        });    }
}
