package com.example.helloworld;

import android.app.Application;

import androidx.room.Room;

public class MyApplication extends Application {
    LocalDataSource localDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        if(localDataSource==null){
            localDataSource = Room.databaseBuilder(getApplicationContext(),
                    LocalDataSource.class, "person").build();
        }
    }

    public LocalDataSource getLocalDataSourceInstance(){
        return localDataSource;
    }
}
