package com.alleviate.movies.models;

/**
 * Created by Shirish Kadam on 21/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class CreditsCastTMdb {

    public String cast_character, cast_name, cast_profile;
    public int cast_gender, cast_order;

    public CreditsCastTMdb(String cast_character, String cast_name, String cast_profile, int cast_gender, int cast_order){
        this.cast_character = cast_character;
        this.cast_name = cast_name;
        this.cast_profile = cast_profile;
        this.cast_gender = cast_gender;
        this.cast_order = cast_order;
    }
}
