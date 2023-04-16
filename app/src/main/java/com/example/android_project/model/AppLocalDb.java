package com.example.android_project.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android_project.MyApplication;

@Database(entities = {User.class,Post.class,Object.class}, version = 55)
abstract class AppLocalDbRepository extends RoomDatabase {
    //    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract ObjectItemDao objectItemDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}
