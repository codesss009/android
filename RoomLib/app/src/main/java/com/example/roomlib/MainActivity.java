package com.example.roomlib;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "notes.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        Notes note = db.notesDao().getNote(2);
        note.notes_text = "im updated version haha loll";
        db.notesDao().update(note);
       // db.notesDao().insertAll(new Notes("notes1"), new Notes("notes2"), new Notes("notes3"));
        Log.d(TAG, String.valueOf(db.notesDao().getAll()));

    }
}