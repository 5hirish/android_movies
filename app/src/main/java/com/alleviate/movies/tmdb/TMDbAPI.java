package com.alleviate.movies.tmdb;

import com.alleviate.movies.RetrofitClient;

/**
 * Created by Shirish Kadam on 18/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class TMDbAPI {

    private TMDbAPI() {}

    private static final String BASE_URL = "https://api.themoviedb.org/";

    public static TMDbAPIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(TMDbAPIService.class);
    }
}
