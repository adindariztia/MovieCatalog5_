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
import com.example.moviecatalog5.R;
import com.example.moviecatalog5.TvShowDetailActivity;
import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w185";
    private Context context;
    private ArrayList<TvShow> mData = new ArrayList<>();

    public void setData(ArrayList<TvShow> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public TvShowAdapter(Context context){
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(mData.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TvShowDetailActivity.class);
                int showId = mData.get(viewHolder.getAdapterPosition()).getId();
                intent.putExtra(TvShowDetailActivity.EXTRA_DETAIL_TV, String.valueOf(showId));
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
        TextView tvJudulTvShow, tvRatingTvShow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_film);
            tvJudulTvShow = itemView.findViewById(R.id.tv_judul_film);
            tvRatingTvShow = itemView.findViewById(R.id.tv_rating_film);
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
