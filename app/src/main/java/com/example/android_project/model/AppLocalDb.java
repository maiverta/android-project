package com.example.android_project.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android_project.Main2Activity;
import com.example.android_project.MyApplication;


@Database(entities = {ObjectItem.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    // abstract fnction that return the dao
    public abstract ObjectItemDao objectItemDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}
