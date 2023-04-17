package com.example.android_project.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post where email != :email AND NOT isTaken")
    LiveData<List<Post>> getAll(String email);

    @Query("select * from Post where email=:emails")
    LiveData<List<Post>> getUsersPosts(String emails);


//    @Query("select * from Post where email != :username AND NOT isTaken")
//    List<ObjectItem> getAllOthersPosts(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post ... posts);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(ObjectItem ... objectItems);
//
    @Query("select * from Post where id = :postId")
    Post getById(String postId);

    @Delete
    void delete(Post post);






}