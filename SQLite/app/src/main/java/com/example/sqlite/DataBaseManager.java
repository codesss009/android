package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataBaseManager {
    Context mContext;
    SQLiteDatabase db;
    DataBaseHelper dbOpenHelper;
    NotesDAO notesDAO;

    public DataBaseManager (Context context){
        this.mContext = context;
        dbOpenHelper = new DataBaseHelper(mContext);

        db = dbOpenHelper.getWritableDatabase();

        notesDAO = new NotesDAO(db);

    }

    public NotesDAO getNotesDAO() {
        return notesDAO;
    }
}
