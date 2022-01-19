package com.elexandro.minhasanotacoes.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NAME_DB = "db_notes";
    public static String TABLE_NAME = "notes";
    public static String ID = "id";
    public static String TITLE = "title";
    public static String DESCRIPTION = "description";
    public static String DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                TITLE + " VARCHAR(50) NOT NULL, " +
                DESCRIPTION + " VARCHAR(500) NOT NULL, " +
                DATE + " VARCHAR(11) NOT NULL);";

        try {
            database.execSQL(sqlCreate);
        } catch (Exception e) {
           //TODO fazer algo aqui depois.
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
