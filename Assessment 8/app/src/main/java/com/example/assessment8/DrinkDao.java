package com.example.assessment8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assessment8.db.Drink;

import java.util.List;

@Dao
public interface DrinkDao {
    @Insert
    public void insertAll(Drink... drink);

    @Query("DELETE FROM drink")
    public void nukeTable();

    @Update
    public void update(Drink drink);

    @Delete
    public void delete(Drink drink);

    @Query("SELECT * FROM drink")
    List<Drink> getAll();

}
