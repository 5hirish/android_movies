package com.alleviate.movies;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Shirish Kadam on 18/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

interface TMDbAPIService {

    @GET("/3/search/movie")
    Call <SearchMovies> search_movies(@QueryMap Map<String, String> params);
}

// https://api.themoviedb.org/3/search/movie?api_key=<api_key>&language=en-US&query=ant-man&include_adult=false