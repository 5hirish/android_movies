package com.alleviate.movies;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.QueryMap;

public class MovieDetailActivity extends AppCompatActivity {

    private TMDbAPIService mAPIService;
    String movie_keyword;
    TextView response_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent movie_intent = getIntent();
        final String movie_title = movie_intent.getStringExtra(Constants.in_movie_title);
        setTitle(movie_title);

        movie_keyword = movie_title;
        response_tv = (TextView)findViewById(R.id.response_text);

        mAPIService = TMDbAPI.getAPIService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.search_movie);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo!=null && networkInfo.isConnected()){
                    get_tmdb_movies(movie_keyword);
                } else {
                    Toast.makeText(getApplicationContext(),"Data Connection Failed...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void get_tmdb_movies(String movie_title) {

        Map<String, String> params = new HashMap<>();
        params.put("api_key", getString(R.string.tmdb_api_key));
        params.put("language", "en-US");
        params.put("query", movie_title);
        params.put("page", String.valueOf(1));
        params.put("include_adult", "false");

        Call <SearchMovies> movies_list = mAPIService.search_movies(params);

        movies_list.enqueue(new Callback <SearchMovies>() {
            @Override
            public void onResponse(Call <SearchMovies> call, Response <SearchMovies> response) {
                if(response.isSuccessful()){
                    response_tv.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call <SearchMovies> call, Throwable t) {
                t.printStackTrace();
                Log.e("Request", "Unable to send GET to API. :: "+t.toString());
            }
        });
    }
}
