package edu.uncc.inclass09;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface GradesDao {

    @Insert
     void insertAll(Grade... grade);

    @Delete
    void delete(Grade grade);

    @Query("SELECT * from Grades Where cid = :cid")
    Grade getGradeByID(int cid);

    @Query("SELECT * from Grades")
    List<Grade> getAll();
}
