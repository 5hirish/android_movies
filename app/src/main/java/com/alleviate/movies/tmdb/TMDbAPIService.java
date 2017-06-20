package com.alleviate.movies.tmdb;

import com.alleviate.movies.pojo.SearchMovies;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Shirish Kadam on 18/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public interface TMDbAPIService {

    @GET("/3/search/movie")
    Call <SearchMovies> search_movies(@QueryMap Map<String, String> params);

    //@GET("/3/movie/{movie_id}/credits")
    //Call <CreditMovies>
}

// https://api.themoviedb.org/3/search/movie?api_key=<api_key>&language=en-US&query=ant-man&include_adult=false