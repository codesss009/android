package com.example.roomlib;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo
    String notes_text;

    public Notes(long id, String notes_text) {
        this.id = id;
        this.notes_text = notes_text;
    }

    public Notes() {
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", notes_text='" + notes_text + '\'' +
                '}';
    }

    public Notes(String notes_text) {
        this.notes_text = notes_text;
    }
}
