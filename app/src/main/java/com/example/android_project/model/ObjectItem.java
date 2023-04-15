package com.example.android_project.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ObjectItem {
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";

    public Integer hand= 0;
    public String city="";

    public String description="";

    public String notes="";

    public String imageUrl="";

    public String username="";

    public String phoneNumber="";

    public Boolean isTaken = false;

    public ObjectItem(){

    }
    public ObjectItem(String id, String name, Integer hand, String city, String imageUrl, String username,String phoneNumber, String description, String notes) {
        this.name = name;
        this.id = id;
        this.hand = hand;
        this.city = city;
        this.imageUrl = imageUrl;
        this.username= username;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.notes = notes;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
