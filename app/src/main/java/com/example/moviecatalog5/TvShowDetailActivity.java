package com.example.moviecatalog5;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalog5.ViewModel.TvShowDetailViewModel;
import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.TvShowDbHelper;
import com.example.moviecatalog5.db.tables.TvShowTable;
import com.example.moviecatalog5.model.TvShow;
import com.example.moviecatalog5.model.TvShowDetail;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_DETAIL_TV = "extra_detail";
    private static final String BASE_URL ="http://image.tmdb.org/t/p/w185";

    TvShowDetailViewModel tvShowDetailViewModel;

    ImageView poster;
    TextView tvJudul, tvGenre, tvProduction, tvSinopsis, lblSinopsis;
    ProgressBar progressBar;
    Button btnFav;

    TvShowDbHelper dbHelper;
    SQLiteDatabase tvShowDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        poster = findViewById(R.id.img_poster_detail_tv_detail);
        tvJudul = findViewById(R.id.tv_judul_tv_detail);
        tvGenre = findViewById(R.id.tv_genre_tv_detail);
        tvProduction = findViewById(R.id.tv_production_tv_detail);
        tvSinopsis = findViewById(R.id.tv_sinopsis_detail_tv_detail);
        lblSinopsis = findViewById(R.id.tv_label_sinopsis_tv);
        progressBar = findViewById(R.id.progressBarTvDetail);
        btnFav = findViewById(R.id.btn_favorite_tv_show);

        //buat database
        dbHelper = new TvShowDbHelper(this);
        tvShowDb = dbHelper.getWritableDatabase();

        showLoading(true);
        tvShowDetailViewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
        String dataIntent = getIntent().getStringExtra(EXTRA_DETAIL_TV);
        tvShowDetailViewModel.setShowDetail(dataIntent);
        tvShowDetailViewModel.getShowDetail().observe(this, new Observer<TvShowDetail>() {
            @Override
            public void onChanged(@Nullable final TvShowDetail tvShowDetail) {
                tvJudul.setText(tvShowDetail.getJudulFilm());
                tvGenre.setText(tvShowDetail.getGenreFilm());
                tvProduction.setText(tvShowDetail.getProductionFilm());
                tvSinopsis.setText(tvShowDetail.getSinopsisFilm());
                Glide.with(getApplicationContext())
                        .load(BASE_URL + tvShowDetail.getPosterFilm())
                        .into(poster);
                showLoading(false);

                btnFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TvShow tvShow = new TvShow(
                                tvShowDetail.getId(),
                                tvShowDetail.getJudulFilm(),
                                tvShowDetail.getRatingShow(),
                                tvShowDetail.getPosterFilm()
                        );
                        TvShowTable.addTvShow(tvShowDb, tvShow);
                        Toast.makeText(getApplicationContext(), tvShowDetail.getJudulFilm() + " successfully added to Favorite!", Toast.LENGTH_SHORT).show();
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
