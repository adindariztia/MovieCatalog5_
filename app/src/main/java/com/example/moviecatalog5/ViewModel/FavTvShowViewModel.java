package com.example.moviecatalog5.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.TvShowDbHelper;
import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.db.tables.TvShowTable;
import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.TvShow;

import java.util.ArrayList;

public class FavTvShowViewModel extends ViewModel {
    TvShowDbHelper dbHelper;
    SQLiteDatabase movieDb;

    private MutableLiveData<ArrayList<TvShow>> tvShowMutableLiveData = new MutableLiveData<>();

    public void setTvShowFav(Context context){
        ArrayList<TvShow> itemTvShow;
        ArrayList<TvShow> tvShowArray = new ArrayList<>();

        dbHelper = new TvShowDbHelper(context);
        movieDb = dbHelper.getWritableDatabase();

        itemTvShow = TvShowTable.getAllTvShows(movieDb);
        for (int i=0; i< itemTvShow.size(); i++){
            TvShow tvShow = new TvShow(
                    itemTvShow.get(i).getId(),
                    itemTvShow.get(i).getJudulShow(),
                    itemTvShow.get(i).getRatingShow(),
                    itemTvShow.get(i).getPosterShow()
            );
            tvShowArray.add(tvShow);
        }
        tvShowMutableLiveData.postValue(tvShowArray);
    }
    public LiveData<ArrayList<TvShow>> getTvShowFav(){ return tvShowMutableLiveData; }
}
