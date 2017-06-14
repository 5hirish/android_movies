package com.alleviate.movies;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View frag_view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = (RecyclerView) frag_view.findViewById(R.id.movie_view);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.shared_preferences_file, Context.MODE_PRIVATE);
        String view_type = sharedPreferences.getString(Constants.sp_view_type, Constants.sp_view_type_card);

        if (view_type.equals(Constants.sp_view_type_card)){
            staggeredGridLayoutManager.setSpanCount(2);
        } else {
            staggeredGridLayoutManager.setSpanCount(1);
        }

        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        String movies[] = getResources().getStringArray(R.array.mcu_titles);
        final ArrayList movies_list = new ArrayList<String>(Arrays.asList(movies));
        Collections.sort(movies_list);

        movieAdapter = new MovieAdapter(getActivity(), movies_list);
        recyclerView.setAdapter(movieAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return frag_view;
    }

}
