package com.example.android_project.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android_project.MyApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
//    public String id;
//    public String title;
//    public String description;
//    public String price;
//    public String price_usd;
//    public String res_name;
//    public String res_address;
//    public String email;
//    public String pic_path;

    public String id="";
//    public String name="";
    public String title="";
    public String description="";
    public String price;
    public String priceUsd;
    public Integer hand= 0;
    public String city="";
    public String email="";
    public String phoneNumber="";
    public Boolean isTaken = false;
    public String notes="";
    public String imagePath="";

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long lastUpdated;
    public Post(){

    }

    public Post(String id, String title, String description, String price, String priceUsd,
                Integer hand, String city, String email,String phoneNumber,Boolean isTaken,
                String notes,String imagePath) {
        this.id = id;
//        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.priceUsd = priceUsd;
        this.hand = hand;
        this.city = city;
        this.email=email;
        this.phoneNumber = phoneNumber;
        this.isTaken = isTaken;
        this.notes = notes;
        this.imagePath=imagePath;
    }

    public static void addPost(String id, String title, String description,
                               String price, String priceUsd, Integer hand, String city, String email,
                               String phoneNumber,Boolean isTaken, String notes,String imagePath) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
//        data.put("name", name );
        data.put("title", title);
        data.put("description", description);
        data.put("price", price);
        data.put("priceUsd",priceUsd);
        data.put("hand",hand);
        data.put("city",city);
        data.put("email", email);
        data.put("phoneNumber",phoneNumber);
        data.put("isTaken",isTaken);
        data.put("notes",notes );
        data.put("imagePath",imagePath);
        data.put("lastUpdated", FieldValue.serverTimestamp());
        Firestore.instance().getDb().collection("posts").document(id).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Document has been added with custom ID");
                        Model.instance().refreshAllPosts();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    static final String ID = "id";
//    static final String NAME = "name";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String PRICE = "price";
    static final String PRICE_USD = "priceUsd";
    static final String HAND = "hand";
    static final String CITY = "city";
    static final String EMAIL = "email";
    static final String PHONE_NUMBER = "phoneNumber";
    static final String IS_TAKEN = "isTaken";
    static final String NOTES = "notes";
    static final String IMAGE_PATH = "imagePath";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
//        String name = (String) json.get(NAME);
        String title = (String)json.get(TITLE);
        String description = (String)json.get(DESCRIPTION);
        String price = String.valueOf(json.get(PRICE));
        String priceUsd = String.format("%.2f", json.get(PRICE_USD));
        Integer hand = (Integer)json.get(HAND);
        String city = (String)json.get(CITY);
        String email = (String)json.get(EMAIL);
        String phoneNumber = (String)json.get(PHONE_NUMBER);
        Boolean isTaken = (boolean)json.get(IS_TAKEN);
        String notes = (String)json.get(NOTES);
        String imagePath = (String)json.get(IMAGE_PATH);
        Timestamp time = (Timestamp) json.get(LAST_UPDATED);

        Post post = new Post( id, title, description, price, priceUsd, hand,
                city, email, phoneNumber, isTaken, notes, imagePath);
        post.setLastUpdated(time.getSeconds());

        return post;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit().putLong(LOCAL_LAST_UPDATED,time).commit();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public Integer getHand() {
        return hand;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public String getImagePath() {
        return imagePath;
    }


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setImagePath(String pic_path) {
        this.imagePath = pic_path;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

}
