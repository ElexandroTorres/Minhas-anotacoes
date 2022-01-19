package com.elexandro.minhasanotacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.elexandro.minhasanotacoes.helpers.DBHelper;
import com.elexandro.minhasanotacoes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesDAO {
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public NotesDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public boolean save(Note note) {
        ContentValues content = new ContentValues();
        content.put("title", note.getTitle());
        content.put("description", note.getDescription());
        content.put("date", note.getDate());

        try {
            write.insert(DBHelper.TABLE_NAME, null, content);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Note> listNotes() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM " + DBHelper.TABLE_NAME + ";";

        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            Note note = new Note();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DATE));

            note.setId(id);
            note.setTitle(title);
            note.setDescription(description);
            note.setDate(date);

            notes.add(note);
        }
        cursor.close();

        return notes;
    }

    public boolean update(Note note) {
        ContentValues content = new ContentValues();
        content.put("title", note.getTitle());
        content.put("description", note.getDescription());
        content.put("date", note.getDate());

        try {
            String[] args = {Integer.toString(note.getId())};

        }
    }
}
