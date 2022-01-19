package com.elexandro.minhasanotacoes.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutionException;

public class DBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NAME_DB = "db_notes";
    public static String NOTES_TABLE = "notes";

    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "title VARCHAR(50) NOT NULL, " +
                "description VARCHAR(500) NOT NULL, " +
                "date VARCHAR(11) NOT NULL);";

        try {
            database.execSQL(sqlCreate);
        } catch (Exception e) {
           //TODO fazer algo aqui depois.
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
