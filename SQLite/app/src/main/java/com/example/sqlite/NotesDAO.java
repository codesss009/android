package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotesDAO {
    SQLiteDatabase db;
    public NotesDAO(SQLiteDatabase db){
        this.db = db;
    }
        public long save(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesTable.SUBJECT,note.getSubject());
        contentValues.put(NotesTable.TEXT,note.getText());

            return db.insert(NotesTable.TABLE_NAME, null, contentValues);
        }
        public boolean update(Note note){
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesTable.SUBJECT,note.getSubject());
            contentValues.put(NotesTable.TEXT,note.getText());
            return db.update(NotesTable.TABLE_NAME, contentValues, NotesTable.COLUMN_ID+ "=? ", new String[]{String.valueOf(note.get_id())} ) > 0;
        }
        public boolean delete(Note note){
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesTable.SUBJECT,note.getSubject());
            contentValues.put(NotesTable.TEXT,note.getText());
            return db.delete(NotesTable.TABLE_NAME, NotesTable.COLUMN_ID+"=? ", new String[]{String.valueOf(note.get_id())})>0;
        }
        public Note get(long id){
        Note note =  null;
        Cursor cursor = db.query(NotesTable.TABLE_NAME, new String[]{NotesTable.COLUMN_ID, NotesTable.SUBJECT, NotesTable.TEXT}, NotesTable.COLUMN_ID+"=? ", new String[]{String.valueOf(id)},null, null, null );
        if(cursor.moveToFirst()){
            note = buildNoteFromCursor(cursor);
        }
        return note;
    }

    public ArrayList<Note> getAll(){
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = db.query(NotesTable.TABLE_NAME, new String[]{NotesTable.COLUMN_ID, NotesTable.SUBJECT, NotesTable.TEXT}, null, null,null, null, null );
        while(cursor.moveToNext()){
            notes.add(buildNoteFromCursor(cursor));
        }
        return notes;
    }
    private Note buildNoteFromCursor(Cursor cursor){
        Note note = new Note();
        note.set_id(cursor.getLong(0));
        note.setSubject(cursor.getString(1));
        note.setText(cursor.getString(2));
        return note;
    }
}
