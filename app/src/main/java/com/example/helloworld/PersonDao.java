package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person... persons);

    @Query("SELECT * FROM person")
    List<Person> getAll();


}
