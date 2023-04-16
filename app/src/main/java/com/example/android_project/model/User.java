package com.example.android_project.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id;
    public String userName;
    public String password;

    public User(){
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


}
