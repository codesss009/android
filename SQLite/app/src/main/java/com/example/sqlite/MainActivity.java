package com.example.sqlite;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DataBaseManager dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DataBaseManager(this);
        dm.getNotesDAO().save(new Note("subject","real_text"));
        dm.getNotesDAO().save(new Note("subject2","real_text1"));
        dm.getNotesDAO().save(new Note("subject3","real_text2"));

        Log.d(TAG, "onCreate: "+ dm.getNotesDAO().getAll());
    }
}