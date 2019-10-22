package com.example.moviecatalog5.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviecatalog5.model.TvShow;

import java.util.ArrayList;

import static com.example.moviecatalog5.db.Const.CMD_CREATE_TABLE_IF_NOT_EXIST;
import static com.example.moviecatalog5.db.Const.COMA;
import static com.example.moviecatalog5.db.Const.LEFT_BRACKET;
import static com.example.moviecatalog5.db.Const.PRIMARY_KEY;
import static com.example.moviecatalog5.db.Const.RIGHT_BRACKET;
import static com.example.moviecatalog5.db.Const.SEMICOLON;
import static com.example.moviecatalog5.db.Const.TYPE_INT;
import static com.example.moviecatalog5.db.Const.TYPE_REAL;
import static com.example.moviecatalog5.db.Const.TYPE_TEXT;
import static com.example.moviecatalog5.db.tables.TvShowTable.Columns.ID;

public class TvShowTable {
    public static final String TABLE_NAME = "tvshow";

    public interface Columns{
        String ID = "id";
        String JUDUL_SHOW = "judulShow";
        String RATING_SHOW = "ratingShow";
        String POSTER = "posterShow";
    }
    public static final String CMD_CREATE =
            CMD_CREATE_TABLE_IF_NOT_EXIST + TABLE_NAME
                + LEFT_BRACKET
                    + ID + TYPE_TEXT + PRIMARY_KEY + COMA
                    + Columns.JUDUL_SHOW + TYPE_TEXT + COMA
                    + Columns.RATING_SHOW + TYPE_REAL + COMA
                    + Columns.POSTER + TYPE_TEXT
                + RIGHT_BRACKET
            + SEMICOLON;

    public static long addTvShow(SQLiteDatabase db, TvShow show){
        ContentValues tvShow = new ContentValues();
        tvShow.put(ID, show.getId());
        tvShow.put(Columns.JUDUL_SHOW, show.getJudulShow());
        tvShow.put(Columns.RATING_SHOW, show.getRatingShow());
        tvShow.put(Columns.POSTER, show.getPosterShow());

        return db.insert(TABLE_NAME, null, tvShow);
    }

    public static boolean deleteMovie(SQLiteDatabase db, int id){
        if (db.delete(TABLE_NAME, ID + "=" + id, null ) > 0){
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<TvShow> getAllTvShows(SQLiteDatabase db){
        ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
        Cursor c = db.query(
                TABLE_NAME,
                new String[] {ID, Columns.JUDUL_SHOW, Columns.RATING_SHOW, Columns.POSTER},
                null, null, null, null, null
        );
        while (c.moveToNext()){
            tvShowArrayList.add(new TvShow(
                    c.getInt(0),
                    c.getString(1),
                    c.getDouble(2),
                    c.getString(3)
            ));
        }
        return tvShowArrayList;
    }

    public static Cursor getAllTvShowCursor(SQLiteDatabase db){
        return db.query(
                TABLE_NAME,
                new String[] {ID, Columns.JUDUL_SHOW, Columns.RATING_SHOW, Columns.POSTER},
                null, null, null, null, null
        );
    }
}
