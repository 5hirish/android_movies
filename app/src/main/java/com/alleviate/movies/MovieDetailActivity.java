package com.alleviate.movies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alleviate.movies.adapters.CasteAdapter;
import com.alleviate.movies.adapters.MovieAdapter;
import com.alleviate.movies.adapters.MovieTMdbAdapter;
import com.alleviate.movies.helper.Constants;
import com.alleviate.movies.models.MovieTMdb;
import com.alleviate.movies.pojo.CreditCast;
import com.alleviate.movies.pojo.CreditCrew;
import com.alleviate.movies.pojo.CreditMovies;
import com.alleviate.movies.pojo.SearchMovies;
import com.alleviate.movies.pojo.SearchMoviesResult;
import com.alleviate.movies.tmdb.TMDbAPI;
import com.alleviate.movies.tmdb.TMDbAPIService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private TMDbAPIService mAPIService;
    String movie_keyword;
    TextView response_tv;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CasteAdapter movieAdapter;

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

        FloatingActionButton search_fab = (FloatingActionButton) findViewById(R.id.search_movie);
        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo!=null && networkInfo.isConnected()){
                    get_tmdb_movies(movie_keyword, false);
                } else {
                    Toast.makeText(getApplicationContext(),"Data Connection Failed...",Toast.LENGTH_SHORT).show();
                }
            }
        });

        search_fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo!=null && networkInfo.isConnected()){
                    get_tmdb_movies(movie_keyword, true);
                } else {
                    Toast.makeText(getApplicationContext(),"Data Connection Failed...",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.movie_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        String movies[] = getResources().getStringArray(R.array.mcu_caste);
        final ArrayList movies_list = new ArrayList<String>(Arrays.asList(movies));
        Collections.sort(movies_list);

        movieAdapter = new CasteAdapter(MovieDetailActivity.this, movies_list);
        recyclerView.setAdapter(movieAdapter);

        //recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void get_tmdb_movie_credits(int movie_id) {

        Call <CreditMovies> movie_credits = mAPIService.get_movie_credits(movie_id, getString(R.string.tmdb_api_key));
        movie_credits.enqueue(new Callback<CreditMovies>() {
            @Override
            public void onResponse(Call<CreditMovies> call, Response<CreditMovies> response) {
                if (response.isSuccessful()){

                    List<CreditCast> movie_cast = response.body().getCast();
                    List<CreditCrew> movie_crew = response.body().getCrew();

                    response_tv.setText(response.body().getId());

                }
            }

            @Override
            public void onFailure(Call<CreditMovies> call, Throwable t) {
                t.printStackTrace();
                Log.e("Request", "Unable to send GET to API. :: "+t.toString());
            }
        });

    }

    public void get_tmdb_movies(final String movie_title, final boolean is_list) {

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

                    ArrayList<MovieTMdb> movie_titles = new ArrayList<MovieTMdb>();
                    ArrayList<SearchMoviesResult> movies_result = response.body().getResults();

                    if (is_list){

                        for (SearchMoviesResult movie:movies_result) {

                            MovieTMdb tMdb = new MovieTMdb(movie.getId(), movie.getOriginalTitle(), movie.getReleaseDate());
                            movie_titles.add(tMdb);
                        }

                        int movie_id = build_movie_dialog(movie_titles);
                        get_tmdb_movie_credits(movie_id);



                    } else {

                        SearchMoviesResult first_match_movie = movies_result.get(0);
                        MovieTMdb tMdb = new MovieTMdb(first_match_movie.getId(), first_match_movie.getOriginalTitle(), first_match_movie.getReleaseDate());
                        movie_titles.add(tMdb);

                        int movie_id = first_match_movie.getId();
                        get_tmdb_movie_credits(movie_id);

                    }

                }
            }

            @Override
            public void onFailure(Call <SearchMovies> call, Throwable t) {
                t.printStackTrace();
                Log.e("Request", "Unable to send GET to API. :: "+t.toString());
            }
        });
    }

    private int build_movie_dialog(ArrayList<MovieTMdb> movies_result) {

        final int[] movie_id = new int[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog_view = (View) inflater.inflate(R.layout.movie_list_dialogue_layout, null);
        //ListView listView = (ListView)dialog_view.findViewById(R.id.dialog_movie_list);

        final ArrayAdapter<MovieTMdb> movie_adapter = new MovieTMdbAdapter<MovieTMdb>(this, movies_result);
        builder.setView(dialog_view)
                .setTitle(R.string.movie_list_dialog)
                .setAdapter(movie_adapter, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       movie_id[0] = movie_adapter.getItem(which).movie_id;
                   }
               });
        builder.create().show();
        return movie_id[0];
    }
}
