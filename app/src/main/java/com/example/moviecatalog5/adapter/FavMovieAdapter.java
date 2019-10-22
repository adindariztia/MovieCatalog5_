package com.example.moviecatalog5.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalog5.MovieDetailActivity;
import com.example.moviecatalog5.R;
import com.example.moviecatalog5.db.MovieDbHelper;

import com.example.moviecatalog5.db.tables.MovieTable;
import com.example.moviecatalog5.model.Movie;

import java.util.ArrayList;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolder>{
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    private Context context;
    private ArrayList<Movie> mData = new ArrayList<>();
    MovieDbHelper dbHelper;
    SQLiteDatabase movieDb;

    public void setData(ArrayList<Movie> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public FavMovieAdapter(Context context){
        this.context = context;
        dbHelper = new MovieDbHelper(context);
        movieDb = dbHelper.getWritableDatabase();
    }



    @NonNull
    @Override
    public FavMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_fav, viewGroup, false);

        return new FavMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavMovieAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.bind(mData.get(i));
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {

            //String judul = mData.get(i).getJudulFilm();
            @Override
            public void onClick(View v) {
                MovieTable.deleteMovie(movieDb, mData.get(i).getId());
                //Toast.makeText(v.getContext(), judul, Toast.LENGTH_SHORT).show();
                mData.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeRemoved(i, mData.size());


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvJudulFilm, tvRatingFilm;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_fav_film);
            tvJudulFilm = itemView.findViewById(R.id.tv_judul_fav_film);
            tvRatingFilm = itemView.findViewById(R.id.tv_rating_fav_film);
            btnDelete = itemView.findViewById(R.id.btn_delete_fav);
        }

        void bind(Movie movie){
            tvJudulFilm.setText(movie.getJudulFilm());
            tvRatingFilm.setText(String.valueOf(movie.getRatingFilm()));

            if (movie.getPosterPath() != null){
                Glide.with(context)
                        .load(BASE_URL + movie.getPosterPath())
                        .into(imgPoster);
            }
        }
    }
}
