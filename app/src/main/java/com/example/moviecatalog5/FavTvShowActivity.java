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
import com.example.moviecatalog5.adapter.FavTvShowAdapter;
import com.example.moviecatalog5.adapter.MovieAdapter;
import com.example.moviecatalog5.adapter.TvShowAdapter;
import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.TvShow;

import java.util.ArrayList;

public class FavTvShowActivity extends AppCompatActivity {
    RecyclerView rvMain;
    FavTvShowAdapter favTvShowAdapter;
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
        favTvShowAdapter = new FavTvShowAdapter(this);
        rvMain.setAdapter(favTvShowAdapter);

    }
    private Observer<ArrayList<TvShow>> getTvShowObserver = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> shows) {
            if(shows != null){
                favTvShowAdapter.setData(shows);

            }
        }
    };
}
