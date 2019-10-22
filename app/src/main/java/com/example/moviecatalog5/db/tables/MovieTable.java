package com.example.moviecatalog5.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviecatalog5.model.Movie;

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
import static com.example.moviecatalog5.db.tables.MovieTable.Columns.ID;

public class MovieTable {
    public static final String TABLE_NAME = "movie";

    public interface Columns{
        String ID = "id";
        String JUDUL_FILM = "judulFilm";
        String RATING_FILM = "ratingFilm";
        String POSTER_FILM = "posterPath";

    }

    public static final String CMD_CREATE =
            CMD_CREATE_TABLE_IF_NOT_EXIST + TABLE_NAME
                + LEFT_BRACKET
                    + ID + TYPE_INT + PRIMARY_KEY + COMA
                    + Columns.JUDUL_FILM + TYPE_TEXT + COMA
                    + Columns.RATING_FILM + TYPE_REAL + COMA
                    + Columns.POSTER_FILM + TYPE_TEXT
                + RIGHT_BRACKET
            + SEMICOLON;

    public static long addMovie(SQLiteDatabase db, Movie movie){
        ContentValues movieRow = new ContentValues();
        movieRow.put(ID, movie.getId());
        movieRow.put(Columns.JUDUL_FILM, movie.getJudulFilm());
        movieRow.put(Columns.RATING_FILM, movie.getRatingFilm());
        movieRow.put(Columns.POSTER_FILM, movie.getPosterPath());

        return db.insert(TABLE_NAME, null, movieRow);
    }

    public static boolean deleteMovie(SQLiteDatabase db, int id){
        if (db.delete(TABLE_NAME, ID + "=" + id, null ) > 0){
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Movie> getAllMovies(SQLiteDatabase db){
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Cursor c = db.query(
                TABLE_NAME,
                new String[] {ID, Columns.JUDUL_FILM, Columns.RATING_FILM, Columns.POSTER_FILM},
                null, null, null, null, null
        );
        while (c.moveToNext()){
            movieArrayList.add(new Movie(
                    c.getInt(0),
                    c.getString(1),
                    c.getDouble(2),
                    c.getString(3)
            ));
        }
        return movieArrayList;
    }

    public static Cursor getAllMovieCursor(SQLiteDatabase db){
        return db.query(
                TABLE_NAME,
                new String[] {ID, Columns.JUDUL_FILM, Columns.RATING_FILM, Columns.POSTER_FILM},
                null, null, null, null, null
        );
    }


}
