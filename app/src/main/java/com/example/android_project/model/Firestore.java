package com.example.android_project.model;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;


public class Firestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final Firestore _instance = new Firestore(db);

    static {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }

    public static Firestore instance() {
        return _instance;
    }

    public Firestore(FirebaseFirestore db) {
        this.db = db;
    }

    public FirebaseFirestore getDb() {
        return this.db;
    }

    Firestore()
    {
        db = FirebaseFirestore.getInstance();
    }

    public void getAllPostsSince(Long since,Model.GetAllPostsListener callback){
        db.collection("posts")
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED,new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Post> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Post st = Post.fromJson(json.getData());
                                list.add(st);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }


    public void getUserPostsSince(Long since,String userEmail,Model.GetAllPostsListener callback){
        db.collection("posts")
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED,new Timestamp(since,0))
                .whereEqualTo(Post.EMAIL, userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Post> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Post st = Post.fromJson(json.getData());
                                list.add(st);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void getPost(String postId, EditText et_title, EditText et_desc, EditText et_price,
                        EditText et_hand, EditText et_city, EditText et_email, EditText et_phoneNumber,
                        EditText et_notes,ImageView imgView){

        DocumentReference docRef = db.collection("posts").document(postId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Post post = Post.fromJson(document.getData());
                    et_title.setText(post.title);
                    et_desc.setText(post.description);
//                    et_name.setText(post.name);
                    et_hand.setText(post.hand);
                    et_city.setText(post.city);
                    et_email.setText(post.email);
                    et_phoneNumber.setText(post.phoneNumber);
                    et_notes.setText(post.notes);
                    Model.instance().getBitMap( post.imagePath,imgView);

                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }

        });

    }


}
