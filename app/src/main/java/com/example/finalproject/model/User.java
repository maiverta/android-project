
package com.example.finalproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String avatarUrl="";
    public String email="";
    public String city="";
    public String phone="";

    public User(){
    }
    public User( String id,String name, String avatarUrl, String email,String city,String phone) {
        this.name = name;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.city = city;
        this.phone = phone;
    }

    public User( String id,String name, String email,String city,String phone) {
        this.name = name;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.city = city;
        this.phone = phone;
    }

    public User( String name, String email,String city,String phone) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.phone = phone;
    }
    static final String NAME = "name";
    static final String ID = "id";
    static final String AVATAR = "avatar";
    static final String EMAIL = "email";
    static final String CITY = "city";
    static final String PHONE = "phone";
    static final String COLLECTION = "users";

    public static User fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
//        String avatar = (String)json.get(AVATAR);
        String email = (String)json.get(EMAIL);
        String city = (String)json.get(CITY);
        String phone = (String)json.get(PHONE);

//        User user = new User(id,name,avatar,email,city,phone);
        User user = new User(id,name,email,city,phone);
        return user;
    }


    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NAME, getName());
        json.put(EMAIL, getEmail());
        json.put(CITY, getCity());
        json.put(PHONE, getPhone());
//        json.put(AVATAR, getAvatarUrl());
        return json;
    }


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

}


