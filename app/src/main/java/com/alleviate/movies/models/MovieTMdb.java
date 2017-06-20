package com.alleviate.movies.models;

/**
 * Created by Shirish Kadam on 20/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class MovieTMdb {
    public String movie_title;
    public String release_date;
    public int movie_id;

    public MovieTMdb(int movie_id, String movie_title, String release_date){
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.release_date = release_date;
    }
}
