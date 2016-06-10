package com.alleviate.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by felix on 10/6/16.
 * Created at Alleviate.
 * shirishkadam.com
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return new MoviesFragment();
            case 1:
                return new MoviesFragment();
            case 2:
                return new MoviesFragment();
            case 3:
                return new MoviesFragment();
            case 4:
                return new MoviesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return Constants.num_tab;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constants.tab_title_movies;
            case 1:
                return Constants.tab_title_rating;
            case 2:
                return Constants.tab_title_directors;
            case 3:
                return Constants.tab_title_actors;
            case 4:
                return Constants.tab_title_genres;

        }
        return null;
    }
}