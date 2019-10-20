package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviecatalog5.ViewModel.ReleaseTodayViewModel;
import com.example.moviecatalog5.adapter.MovieAdapter;
import com.example.moviecatalog5.model.Movie;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReleaseTodayActivity extends AppCompatActivity {
    RecyclerView rvMain;
    MovieAdapter movieAdapter;
    ReleaseTodayViewModel releaseTodayViewModel;
    private ProgressBar progressBar;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_today);

        rvMain = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.progressBar);

        //test = findViewById(R.id.test_tanggal);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        //test.setText(dateFormat.format(date));


        releaseTodayViewModel = ViewModelProviders.of(this).get(ReleaseTodayViewModel.class);
        releaseTodayViewModel.setReleaseTodayMovie(String.valueOf(dateFormat.format(date)));
        releaseTodayViewModel.getTodayMovies().observe(this, getMovies);
        showLoading(true);

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this);
        rvMain.setAdapter(movieAdapter);
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if(movies != null){
                movieAdapter.setData(movies);
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
}
