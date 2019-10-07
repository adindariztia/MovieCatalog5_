package com.example.moviecatalog5.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalog5.MovieDetailActivity;
import com.example.moviecatalog5.R;
import com.example.moviecatalog5.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    private Context context;
    private ArrayList<Movie> mData = new ArrayList<>();

    public void setData(ArrayList<Movie> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context){
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(mData.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                int movieId = mData.get(viewHolder.getAdapterPosition()).getId();
                intent.putExtra(MovieDetailActivity.EXTRA_DETAIL, String.valueOf(movieId));
                v.getContext().startActivity(intent);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_film);
            tvJudulFilm = itemView.findViewById(R.id.tv_judul_film);
            tvRatingFilm = itemView.findViewById(R.id.tv_rating_film);
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
