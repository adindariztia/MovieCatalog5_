package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.moviecatalog5.ViewModel.FavMovieViewModel;
import com.example.moviecatalog5.adapter.FavMovieAdapter;
import com.example.moviecatalog5.adapter.MovieAdapter;
import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.model.Movie;

import java.util.ArrayList;

public class FavoriteMovieActivity extends AppCompatActivity {
    RecyclerView rvMain;
    FavMovieAdapter favMovieAdapter;
    FavMovieViewModel favMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        favMovieViewModel = ViewModelProviders.of(this).get(FavMovieViewModel.class);

        rvMain = findViewById(R.id.rv_main);
        favMovieViewModel.setMovieFav(this);
        favMovieViewModel.getMoviesFav().observe(this, getMovieObserver);


        rvMain.setLayoutManager(new LinearLayoutManager(this));
        favMovieAdapter = new FavMovieAdapter(this);
        rvMain.setAdapter(favMovieAdapter);

    }
    private Observer<ArrayList<Movie>> getMovieObserver = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if(movies != null){
                favMovieAdapter.setData(movies);

            }
        }
    };

}
