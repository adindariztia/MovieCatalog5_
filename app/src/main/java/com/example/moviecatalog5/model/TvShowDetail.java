package com.example.moviecatalog5.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class TvShowDetail {
    private int id;
    private String judulFilm;
    private String genreFilm;
    private String productionFilm;
    private String sinopsisFilm;
    private String posterFilm;
    private double ratingShow;

    public double getRatingShow() {
        return ratingShow;
    }

    public void setRatingShow(double ratingShow) {
        this.ratingShow = ratingShow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudulFilm() {
        return judulFilm;
    }

    public void setJudulFilm(String judulFilm) {
        this.judulFilm = judulFilm;
    }

    public String getGenreFilm() {
        return genreFilm;
    }

    public void setGenreFilm(String genreFilm) {
        this.genreFilm = genreFilm;
    }

    public String getProductionFilm() {
        return productionFilm;
    }

    public void setProductionFilm(String productionFilm) {
        this.productionFilm = productionFilm;
    }

    public String getSinopsisFilm() {
        return sinopsisFilm;
    }

    public void setSinopsisFilm(String sinopsisFilm) {
        this.sinopsisFilm = sinopsisFilm;
    }

    public String getPosterFilm() {
        return posterFilm;
    }

    public void setPosterFilm(String posterFilm) {
        this.posterFilm = posterFilm;
    }

    public TvShowDetail(JSONObject object){
        try{
            int id = object.getInt("id");
            String judul = object.getString("original_name");
            JSONArray genres = object.getJSONArray("genres"); //.getJSONObject(0).getString("name");
            String genre = "";
            for (int i =0; i < genres.length(); i++){
                if (i == 0){
                    genre = genres.getJSONObject(i).getString("name");
                } else {
                    genre += ", " + genres.getJSONObject(i).getString("name");
                }
            }
            String poster = object.getString("poster_path");
            JSONArray prodArr = object.getJSONArray("production_companies");
            String production = "";
            for (int i=0; i < prodArr.length(); i++){
                if (i == 0){
                    production = prodArr.getJSONObject(i).getString("name");
                } else {
                    production += ", " + prodArr.getJSONObject(i).getString("name");
                }

            }
            String sinopsis = object.getString("overview");
            double rating = object.getDouble("vote_average");

            this.id = id;
            this.judulFilm = judul;
            this.genreFilm = genre;
            this.productionFilm = production;
            this.sinopsisFilm = sinopsis;
            this.posterFilm = poster;
            this.ratingShow = rating;



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
