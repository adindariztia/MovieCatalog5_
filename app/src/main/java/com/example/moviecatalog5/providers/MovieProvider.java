package com.example.moviecatalog5.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.tables.MovieTable;

public class MovieProvider extends ContentProvider {

    public static final String URI_AUTHORITY = "com.example.moviecatalog5";
    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String URI_PATH = "movie";
    public static final int URI_CODE_MOVIES = 1;
    SQLiteDatabase movieDb;

    public MovieProvider() {
    }



    static {
        uriMatcher.addURI(URI_AUTHORITY, URI_PATH, URI_CODE_MOVIES);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());
        movieDb = movieDbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)){
            case URI_CODE_MOVIES:
                return MovieTable.getAllMovieCursor(movieDb);
            case UriMatcher.NO_MATCH:default:
                break;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
