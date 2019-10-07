package com.example.moviecatalog5.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.model.Movie;

import java.util.ArrayList;

public class FavMovieViewModel extends ViewModel {
    MovieDbHelper dbHelper;
    SQLiteDatabase movieDb;

    private MutableLiveData<ArrayList<Movie>> movieMutableLiveData = new MutableLiveData<>();

    public void setMovieFav(Context context){
        ArrayList<Movie> itemMovie;
        ArrayList<Movie> movieArray = new ArrayList<>();

        dbHelper = new MovieDbHelper(context);
        movieDb = dbHelper.getWritableDatabase();

        itemMovie = MovieTable.getAllMovies(movieDb);
        for (int i=0; i< itemMovie.size(); i++){
            Movie movie = new Movie(
                    itemMovie.get(i).getId(),
                    itemMovie.get(i).getJudulFilm(),
                    itemMovie.get(i).getRatingFilm(),
                    itemMovie.get(i).getPosterPath()
            );
            movieArray.add(movie);
        }
        movieMutableLiveData.postValue(movieArray);
    }
    public LiveData<ArrayList<Movie>> getMoviesFav(){ return movieMutableLiveData; }
}
