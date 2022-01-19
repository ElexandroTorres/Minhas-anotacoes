package com.elexandro.minhasanotacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.elexandro.minhasanotacoes.helpers.DBHelper;
import com.elexandro.minhasanotacoes.model.Note;

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
        content.put("title", note.get_title());
        content.put("description", note.get_description());
        content.put("date", note.get_date());

        try {
            write.insert(DBHelper.NOTES_TABLE, null, content);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
