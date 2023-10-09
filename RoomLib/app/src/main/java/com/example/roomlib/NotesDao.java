package com.example.roomlib;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insertAll(Notes... notes);

    @Update
    void update(Notes notes);

    @Delete
    void delete(Notes notes);

    @Query("SELECT * from notes")
    List<Notes> getAll();

    @Query(("SELECT * from notes WHERE id = :id"))
    Notes getNote(long id);


}
