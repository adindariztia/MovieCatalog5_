package com.example.moviecatalog5.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.db.tables.TvShowTable;

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "catalogMovie.db";
    public static final int DB_VERSION = 1;

    public MovieDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(MovieTable.CMD_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
