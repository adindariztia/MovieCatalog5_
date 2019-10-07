package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalog5.ViewModel.MovieDetailViewModel;
import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.MovieDetail;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_DETAIL = "extra_detail";
    private static final String BASE_URL ="http://image.tmdb.org/t/p/w185";
    ImageView imgPoster;

    TextView tvJudul, tvGenre, tvProduction, tvSinopsis, lblSinopsis;
    ProgressBar progressBar;
    Button btnFav;
    ArrayList<Movie> arrayMovie;

    MovieDetailViewModel movieDetailViewModel;

    MovieDbHelper dbHelper;
    SQLiteDatabase movieDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgPoster = findViewById(R.id.img_poster_detail);
        tvJudul = findViewById(R.id.tv_judul);
        tvGenre = findViewById(R.id.tv_genre);
        tvProduction = findViewById(R.id.tv_production);
        tvSinopsis = findViewById(R.id.tv_sinopsis_detail);
        lblSinopsis = findViewById(R.id.tv_label_sinopsis);
        progressBar = findViewById(R.id.progressBar);
        btnFav = findViewById(R.id.btn_favorite_movie);


        dbHelper = new MovieDbHelper(this);
        movieDb = dbHelper.getWritableDatabase();


        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        String dataIntent = getIntent().getStringExtra(EXTRA_DETAIL);
        showLoading(true);
        movieDetailViewModel.setDetailMovie(dataIntent);
        movieDetailViewModel.getDetail().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(@Nullable final MovieDetail movieDetail) {
                tvJudul.setText(movieDetail.getJudulFilm());
                tvGenre.setText(movieDetail.getGenreFilm());
                tvProduction.setText(movieDetail.getProductionFilm());
                tvSinopsis.setText(movieDetail.getSinopsisFilm());
                Glide.with(getApplicationContext())
                        .load(BASE_URL + movieDetail.getPosterFilm())
                        .into(imgPoster);
                showLoading(false);

                btnFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Movie movie = new Movie(
                                movieDetail.getId(),
                                movieDetail.getJudulFilm(),
                                movieDetail.getRatingMovie(),
                                movieDetail.getPosterFilm()
                        );
                        MovieTable.addMovie(movieDb, movie);
                        Toast.makeText(getApplicationContext(), movieDetail.getJudulFilm() + " succesfully added to Favorite!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
            lblSinopsis.setVisibility(View.GONE);
            btnFav.setVisibility(View.GONE);

        } else {

            progressBar.setVisibility(View.GONE);
            lblSinopsis.setVisibility(View.VISIBLE);
            btnFav.setVisibility(View.VISIBLE);
        }
    }
}
