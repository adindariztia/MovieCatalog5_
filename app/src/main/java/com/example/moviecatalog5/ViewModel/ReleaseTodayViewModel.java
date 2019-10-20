package com.example.moviecatalog5.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalog5.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ReleaseTodayViewModel extends ViewModel {
    public static final String API_KEY = "eb517e3d5b1ce90ec262788b4117cfb5";
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public void setReleaseTodayMovie(String date){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY +"&primary_release_date.gte="+ date +"&primary_release_date.lte=" + date;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i=0; i < list.length(); i++){
                        JSONObject jsonMovie = list.getJSONObject(i);
                        Movie movie = new Movie(jsonMovie);
                        listItems.add(movie);
                    }
                    listMovie.postValue(listItems);

                } catch (Exception e){
                    Log.d("Exception get movies: ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure movie: ", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getTodayMovies(){return listMovie;}
}
