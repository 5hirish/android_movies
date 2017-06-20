package com.alleviate.movies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alleviate.movies.R;
import com.alleviate.movies.adapters.MetaDataAdapter;
import com.alleviate.movies.ui.DividerItemDecoration;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {


    private RecyclerView.Adapter rvadpter;
    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag_view = inflater.inflate(R.layout.fragment_rating, container, false);

        String movies[] = getResources().getStringArray(R.array.mcu_titles);
        final ArrayList mcu_movies = new ArrayList<String>(Arrays.asList(movies));
        Collections.sort(mcu_movies);

        DragScrollBar dragScrollBar = (DragScrollBar)frag_view.findViewById(R.id.dragScrollBar);

        RecyclerView rv = (RecyclerView)frag_view.findViewById(R.id.rating_view);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager rvlayoutmanager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvlayoutmanager);

        rvadpter = new MetaDataAdapter(getActivity(), mcu_movies);
        rv.setAdapter(rvadpter);

        //dragScrollBar.setHandleColour(getActivity().getColor(R.color.colorAccent));
        dragScrollBar.addIndicator(new AlphabetIndicator(getActivity()), true);

        rv.setItemAnimator(new DefaultItemAnimator());
        return frag_view;
    }

}
