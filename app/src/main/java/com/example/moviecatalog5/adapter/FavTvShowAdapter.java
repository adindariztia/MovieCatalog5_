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

import com.bumptech.glide.Glide;
import com.example.moviecatalog5.R;
import com.example.moviecatalog5.TvShowDetailActivity;
import com.example.moviecatalog5.db.MovieDbHelper;
import com.example.moviecatalog5.db.TvShowDbHelper;
import com.example.moviecatalog5.db.tables.TvShowTable;
import com.example.moviecatalog5.model.TvShow;

import java.util.ArrayList;

public class FavTvShowAdapter extends RecyclerView.Adapter<FavTvShowAdapter.ViewHolder> {
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    private Context context;
    private ArrayList<TvShow> mData = new ArrayList<>();
    TvShowDbHelper dbHelper;
    SQLiteDatabase showDb;

    public void setData(ArrayList<TvShow> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavTvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_fav, viewGroup, false);

        return new FavTvShowAdapter.ViewHolder(view);
    }

    public FavTvShowAdapter(Context context){
        this.context = context;
        dbHelper = new TvShowDbHelper(context);
        showDb = dbHelper.getWritableDatabase();
    }

    @Override
    public void onBindViewHolder(@NonNull final FavTvShowAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.bind(mData.get(i));
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvShowTable.deleteMovie(showDb, mData.get(i).getId());
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
        TextView tvJudulTvShow, tvRatingTvShow;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_fav_film);
            tvJudulTvShow = itemView.findViewById(R.id.tv_judul_fav_film);
            tvRatingTvShow = itemView.findViewById(R.id.tv_rating_fav_film);
            btnDelete = itemView.findViewById(R.id.btn_delete_fav);
        }

        void bind(TvShow show){
            tvJudulTvShow.setText(show.getJudulShow());
            tvRatingTvShow.setText(String.valueOf(show.getRatingShow()));
            Glide.with(context)
                    .load(BASE_URL + show.getPosterShow())
                    .into(imgPoster);
        }
    }
}
