package com.example.moviecatalog5.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalog5.model.MovieDetail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieDetailViewModel extends ViewModel {
    private static final String API_KEY = "eb517e3d5b1ce90ec262788b4117cfb5";
    private MutableLiveData<MovieDetail> details = new MutableLiveData<>();

    public void setDetailMovie(final String movieId){
        String url = "https://api.themoviedb.org/3/movie/"+ movieId +"?api_key="+ API_KEY +"&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    MovieDetail detail = new MovieDetail(responseObject);
                    details.postValue(detail);


                } catch (Exception e){
                    Log.d("Exception detail mov:", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public LiveData<MovieDetail> getDetail(){return details;}
}
