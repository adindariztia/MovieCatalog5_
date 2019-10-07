package com.example.moviecatalog5.model;

import org.json.JSONObject;

public class TvShow {
    private int id;
    private String judulShow;
    private double ratingShow;
    private String posterShow;

    public TvShow(Integer id, String judulShow, double ratingShow, String posterShow){
        this.id = id;
        this.judulShow = judulShow;
        this.ratingShow = ratingShow;
        this.posterShow = posterShow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudulShow() {
        return judulShow;
    }

    public void setJudulShow(String judulShow) {
        this.judulShow = judulShow;
    }

    public double getRatingShow() {
        return ratingShow;
    }

    public void setRatingShow(double ratingShow) {
        this.ratingShow = ratingShow;
    }

    public String getPosterShow() {
        return posterShow;
    }

    public void setPosterShow(String posterShow) {
        this.posterShow = posterShow;
    }

    public TvShow(JSONObject object){
        try{
            int id = object.getInt("id");
            double rating = object.getDouble("vote_average");
            String judul = object.getString("original_name");
            String poster = object.getString("poster_path");

            this.id = id;
            this.ratingShow = rating;
            this.judulShow = judul;
            this.posterShow = poster;

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
