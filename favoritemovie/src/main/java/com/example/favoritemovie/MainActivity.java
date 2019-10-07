package com.example.favoritemovie;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.favoritemovie.adapter.MovieAdapter;
import com.example.favoritemovie.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MovieAdapter movieAdapter;
    Movie movie;
    RecyclerView rvMain;
    ArrayList<Movie> movieArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rv_main);
        movieAdapter = new MovieAdapter(this);
        rvMain.setAdapter(movieAdapter);

        final ContentResolver cr = getContentResolver();

        Cursor movieCursor = cr.query(
                Uri.parse("content://com.example.moviecatalog5/movie"),
                null,
                null,
                null,
                null
        );
        ArrayList<Movie> newMovie = new ArrayList<>();

        while (movieCursor.moveToNext()){
            newMovie.add(
                    new Movie(
                            movieCursor.getInt(0),
                            movieCursor.getString(1),
                            movieCursor.getDouble(2),
                            movieCursor.getString(3)
                    )
            );
        }
        movieAdapter.setData(newMovie);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
    }
}
