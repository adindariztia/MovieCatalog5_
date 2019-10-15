package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalog5.ViewModel.MovieSearchViewModel;
import com.example.moviecatalog5.ViewModel.MovieViewModel;
import com.example.moviecatalog5.adapter.MovieAdapter;
import com.example.moviecatalog5.model.Movie;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    private MovieSearchViewModel movieSearchViewModel;
    MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        rvMovies = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.progressBar);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieSearchViewModel = ViewModelProviders.of(this).get(MovieSearchViewModel.class);

        searchView = findViewById(R.id.search_view);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this);
        rvMovies.setAdapter(movieAdapter);
        showLoading(true);

        if (savedInstanceState == null){
            movieViewModel.setMovie();
            showLoading(true);

        }
        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                if(movies != null){
                    movieAdapter.setData(movies);
                    showLoading(false);
                }
            }
        });





        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieAdapter = new MovieAdapter(MainActivity.this);
                rvMovies.setAdapter(movieAdapter);
                movieSearchViewModel.setMovieSearch(query);
                movieSearchViewModel.getMovieSearch().observe(MainActivity.this, getMovie);
                showLoading(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieViewModel.setMovie();
                movieViewModel.getMovies().observe(MainActivity.this, getMovie);
                movieAdapter = new MovieAdapter(MainActivity.this);
                rvMovies.setAdapter(movieAdapter);
                showLoading(true);
                return true;
            }
        });
    }


    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_movie) {

        } else if (id == R.id.nav_tv_show) {
            Intent intent = new Intent(this, TvShowActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_fav_movie) {
            Intent intent = new Intent(this, FavoriteMovieActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_fav_tv_show) {
            Intent intent = new Intent(this, FavTvShowActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
