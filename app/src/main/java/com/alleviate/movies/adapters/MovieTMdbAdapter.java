package com.alleviate.movies.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alleviate.movies.R;
import com.alleviate.movies.models.MovieTMdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shirish Kadam on 20/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class MovieTMdbAdapter<MovieTMdbtype> extends ArrayAdapter<MovieTMdb>{

    Context context;
    ArrayList<MovieTMdb> movieTMdbs;

    private static class ViewHolder {
        private TextView movie_item;
    }


    public MovieTMdbAdapter(@NonNull Context context, ArrayList<MovieTMdb> movieTMdbs) {
        super(context, 0,movieTMdbs);
        this.context = context;
        this.movieTMdbs = movieTMdbs;
    }

    @Override
    public int getCount() {
        return movieTMdbs.size();
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item_dialogue_layout, parent, false);
        }

        TextView movie_title_tv = (TextView) convertView.findViewById(R.id.movie_title);
        String release_date_str = movieTMdbs.get(position).release_date;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date release_date = sdf.parse(release_date_str);
            Calendar release_date_cal = Calendar.getInstance();
            release_date_cal.setTime(release_date);

            release_date_str = String.valueOf(release_date_cal.get(Calendar.YEAR));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        movie_title_tv.setText(movieTMdbs.get(position).movie_title+" ("+release_date_str+")");

        return convertView;
    }
}
