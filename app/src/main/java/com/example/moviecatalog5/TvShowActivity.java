package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.moviecatalog5.ViewModel.MovieViewModel;
import com.example.moviecatalog5.ViewModel.TvShowSearchViewModel;
import com.example.moviecatalog5.ViewModel.TvShowViewModel;
import com.example.moviecatalog5.adapter.MovieAdapter;
import com.example.moviecatalog5.adapter.TvShowAdapter;
import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.TvShow;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class TvShowActivity extends AppCompatActivity {
    private RecyclerView rvShow;
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBar;
    private TvShowViewModel tvShowViewModel;
    private TvShowSearchViewModel tvShowSearchViewModel;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        rvShow = findViewById(R.id.rv_main_tvShow);
        progressBar = findViewById(R.id.progressBarTvShow);
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowSearchViewModel = ViewModelProviders.of(this).get(TvShowSearchViewModel.class);
        tvShowViewModel.setShow();
        tvShowViewModel.getShows().observe(this, getShow);

        searchView = findViewById(R.id.search_view);

        rvShow.setLayoutManager(new LinearLayoutManager(this));
        tvShowAdapter = new TvShowAdapter(this);
        rvShow.setAdapter(tvShowAdapter);
        showLoading(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvShowAdapter = new TvShowAdapter(TvShowActivity.this);
                rvShow.setAdapter(tvShowAdapter);
                tvShowSearchViewModel.setShowSearch(query);
                tvShowSearchViewModel.getShowsSearch().observe(TvShowActivity.this, getShow);
                showLoading(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tvShowViewModel.setShow();
                tvShowViewModel.getShows().observe(TvShowActivity.this, getShow);
                tvShowAdapter = new TvShowAdapter(TvShowActivity.this);
                rvShow.setAdapter(tvShowAdapter);
                showLoading(true);

                return true;
            }
        });
    }

    private Observer<ArrayList<TvShow>> getShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> shows) {
            if(shows != null){
                tvShowAdapter.setData(shows);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);

        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
}
