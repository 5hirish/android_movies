package com.alleviate.movies.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shirish Kadam on 20/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class TMDbGenres {

    public static Map<Integer,String> genre_map = new HashMap<Integer, String>();

    public TMDbGenres(){

        genre_map.put(28,"Action");
        genre_map.put(12,"Adventure");
        genre_map.put(16,"Animation");
        genre_map.put(35,"Comedy");
        genre_map.put(80,"Crime");
        genre_map.put(99,"Documentary");
        genre_map.put(18,"Drama");
        genre_map.put(10751,"Family");
        genre_map.put(14,"Fantasy");
        genre_map.put(36,"History");
        genre_map.put(27,"Horror");
        genre_map.put(10402, "Music");
        genre_map.put(9648, "Mystery");
        genre_map.put(10749, "Romance");
        genre_map.put(878, "Science Fiction");
        genre_map.put(10770, "TV Movie");
        genre_map.put(53, "Thriller");
        genre_map.put(10752, "War");
        genre_map.put(37, "Western");

    }
}

