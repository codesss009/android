package com.example.sqlite;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "notes.db";
    final static int DATABASE_VERSION = 9;

public DataBaseHelper(Context mContext){
    super(mContext, DATABASE_NAME, null,  DATABASE_VERSION );
}
    @Override
    public void onCreate(SQLiteDatabase db) {
            NotesTable.onCreate(db);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            NotesTable.onUpgrade(db,i, i1);
        NotesTable.onCreate(db);
        Log.d(TAG, "onCreate: ");
    }
}
