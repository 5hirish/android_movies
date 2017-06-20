package com.alleviate.movies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alleviate.movies.adapters.SectionsPagerAdapter;
import com.alleviate.movies.helper.Constants;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Movie Junkies");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private MenuItem card_view, list_view;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        card_view = menu.findItem(R.id.action_switch_card);
        list_view = menu.findItem(R.id.action_switch_list);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.shared_preferences_file,MODE_PRIVATE);
        String view_type = sharedPreferences.getString(Constants.sp_view_type, Constants.sp_view_type_card);

        if (view_type.equals(Constants.sp_view_type_card)){

            list_view.setVisible(true);
            card_view.setVisible(false);

        } else {

            list_view.setVisible(false);
            card_view.setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search){
            return true;
        } else if (id == R.id.action_switch_card) {

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.shared_preferences_file,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.sp_view_type, Constants.sp_view_type_card);
            editor.commit();

            list_view.setVisible(true);
            card_view.setVisible(false);

            mSectionsPagerAdapter.notifyDataSetChanged();

        } else if (id == R.id.action_switch_list) {

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.shared_preferences_file,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.sp_view_type, Constants.sp_view_type_list);
            editor.commit();

            list_view.setVisible(false);
            card_view.setVisible(true);

            mSectionsPagerAdapter.notifyDataSetChanged();

        }


        return super.onOptionsItemSelected(item);
    }
}
