package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moviecatalog5.ViewModel.FavMovieViewModel;
import com.example.moviecatalog5.ViewModel.FavTvShowViewModel;
import com.example.moviecatalog5.adapter.MovieAdapter;
import com.example.moviecatalog5.adapter.TvShowAdapter;
import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.TvShow;

import java.util.ArrayList;

public class FavTvShowActivity extends AppCompatActivity {
    RecyclerView rvMain;
    TvShowAdapter tvShowAdapter;
    FavTvShowViewModel favTvShowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_tv_show);

        favTvShowViewModel = ViewModelProviders.of(this).get(FavTvShowViewModel.class);
        favTvShowViewModel.setTvShowFav(this);
        favTvShowViewModel.getTvShowFav().observe(this, getTvShowObserver);

        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        tvShowAdapter = new TvShowAdapter(this);
        rvMain.setAdapter(tvShowAdapter);

    }
    private Observer<ArrayList<TvShow>> getTvShowObserver = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> shows) {
            if(shows != null){
                tvShowAdapter.setData(shows);

            }
        }
    };
}
