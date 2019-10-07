package com.example.favoritemovie.model;



public class Movie {
    private int id;
    private String judulFilm;
    private double ratingFilm;
    private String posterPath;

    public Movie(Integer id, String judulFilm, double ratingFilm, String posterPath){
        this.id = id;
        this.judulFilm = judulFilm;
        this.ratingFilm = ratingFilm;
        this.posterPath = posterPath;
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

    public double getRatingFilm() {
        return ratingFilm;
    }

    public void setRatingFilm(double ratingFilm) {
        this.ratingFilm = ratingFilm;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


}
