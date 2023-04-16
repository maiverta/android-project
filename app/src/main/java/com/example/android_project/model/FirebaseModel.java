package com.example.android_project.model;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseModel{
    FirebaseFirestore db;

    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

//    public void getAllUsers(Model.GetAllUsersListener callback){
//        db.collection(User.COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<User> list = new LinkedList<>();
//                if (task.isSuccessful()){
//                    QuerySnapshot jsonsList = task.getResult();
//                    for (DocumentSnapshot json: jsonsList){
//                        User user = User.fromJson(json.getData());
//                        list.add(user);
//                    }
//                }
//                callback.onComplete(list);
//            }
//        });
//    }

//    public void addUser(User user, Model.AddUserListener listener) {
//        db.collection(User.COLLECTION).document().set(user.toJson())
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        listener.onComplete();
//                    }
//                });
//    }
}
