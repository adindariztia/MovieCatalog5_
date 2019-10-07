package com.example.moviecatalog5.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalog5.model.Movie;
import com.example.moviecatalog5.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    public static final String API_KEY = "eb517e3d5b1ce90ec262788b4117cfb5";
    private MutableLiveData<ArrayList<TvShow>> listShow = new MutableLiveData<>();

    public void setShow(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+ API_KEY +"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");


                    for (int i =0; i < list.length(); i++){
                        JSONObject jsonShow = list.getJSONObject(i);
                        TvShow show = new TvShow(jsonShow);
                        listItems.add(show);
                        //Log.d("filepath :", show.getPosterPath());
                    }
                    listShow.postValue(listItems);

                } catch (Exception e){
                    Log.d("Exception get tvshow : ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure ",error.getMessage() );
            }
        });
    }
    public LiveData<ArrayList<TvShow>> getShows(){
        return listShow;
    }
}
