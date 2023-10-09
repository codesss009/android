package com.example.sqlite;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesTable {
    final static String TABLE_NAME = "notes";
    final static String COLUMN_ID = "id";
    final static String SUBJECT = "subject";
    final static String TEXT = "text";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + NotesTable.TABLE_NAME);
        sb.append("( "+ COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(SUBJECT+" text not null, ");
        sb.append(TEXT+" text not null); ");
        try {
            db.execSQL(sb.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    static public void onUpgrade(SQLiteDatabase db, int i, int i1){
            try{
                db.execSQL("DROP TABLE IF EXISTS "
                +NotesTable.TABLE_NAME);
            }catch(SQLException e){
                e.printStackTrace();
        }
    }

}
