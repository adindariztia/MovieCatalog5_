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

public class MovieSearchViewModel extends ViewModel {
    public static final String API_KEY = "eb517e3d5b1ce90ec262788b4117cfb5";
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public void setMovieSearch(String query){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY +"&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    String hasil = object.getString("total_results");

                    if (hasil != "0"){
                        JSONArray list = object.getJSONArray("results");
                        for (int i=0; i < list.length(); i++){
                            JSONObject jsonMovie = list.getJSONObject(i);
                            Movie movie = new Movie(jsonMovie);
                            listItems.add(movie);
                        }
                        listMovie.postValue(listItems);

                    }
                }catch (Exception e){
                    Log.d("Exception get movies: ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure movie: ", error.getMessage());
            }
        });

    }
    public LiveData<ArrayList<Movie>> getMovieSearch(){return listMovie;}
}
