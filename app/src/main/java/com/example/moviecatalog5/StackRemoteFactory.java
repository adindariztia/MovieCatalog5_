package com.example.moviecatalog5;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    private Context mContext;
    private Cursor c;
    MovieDbHelper dbHelper;
    SQLiteDatabase moviedb;
    ArrayList<Movie> data;

    public StackRemoteFactory(Context context, Intent intent){
        mContext = context;
        dbHelper = new MovieDbHelper(context);
    }

    @Override
    public void onCreate() {
        ArrayList<Movie> itemMovie;
        moviedb = dbHelper.getWritableDatabase();
        itemMovie = MovieTable.getAllMovies(moviedb);

        for (int i = 0; i < itemMovie.size(); i++){
            Movie movie = new Movie(
                    itemMovie.get(i).getId(),
                    itemMovie.get(i).getJudulFilm(),
                    itemMovie.get(i).getRatingFilm(),
                    itemMovie.get(i).getPosterPath()
            );
            data.add(movie);
        }

//        c = mContext.getContentResolver().query(
//                Uri.parse("content://com.example.moviecatalog5/movie"),
//                null,
//                null,
//                null,
//                null
//        );

    }

    private Movie getMovieItem(int index){
        Movie result = new Movie(
                data.get(index).getId(),
                data.get(index).getJudulFilm(),
                data.get(index).getRatingFilm(),
                data.get(index).getPosterPath());
        return result;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie res = getMovieItem(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BASE_URL + res.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extra = new Bundle();
        extra.putInt(FavMoviesWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extra);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;
        //return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
