package com.alleviate.movies;

/**
 * Created by Shirish Kadam on 18/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class TMDbAPI {

    private TMDbAPI() {}

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static TMDbAPIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(TMDbAPIService.class);
    }
}
