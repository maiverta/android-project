package com.example.android_project.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ObjectItemDao {
    @Query("select * from ObjectItem where username != :username AND NOT isTaken")
    List<ObjectItem> getAllOthers(String username);

    @Query("select * from ObjectItem where username = :username")
    List<ObjectItem> getMyObjects(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ObjectItem ... objectItems);
    @Query("select * from ObjectItem where id = :objectItemId")
    ObjectItem getById(String objectItemId);
}
