package com.alleviate.movies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alleviate.movies.adapters.CastAdapter;
import com.alleviate.movies.adapters.CrewAdapter;
import com.alleviate.movies.adapters.MovieTMdbAdapter;
import com.alleviate.movies.helper.Constants;
import com.alleviate.movies.models.CreditsCastTMdb;
import com.alleviate.movies.models.CreditsCrewTMdb;
import com.alleviate.movies.models.MovieTMdb;
import com.alleviate.movies.pojo.CreditCast;
import com.alleviate.movies.pojo.CreditCrew;
import com.alleviate.movies.pojo.CreditMovies;
import com.alleviate.movies.pojo.DetailMovies;
import com.alleviate.movies.pojo.DetailMoviesGenre;
import com.alleviate.movies.pojo.DetailMoviesProductionCompany;
import com.alleviate.movies.pojo.SearchMovies;
import com.alleviate.movies.pojo.SearchMoviesResult;
import com.alleviate.movies.tmdb.TMDbAPI;
import com.alleviate.movies.tmdb.TMDbAPIService;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private TMDbAPIService mAPIService;
    String movie_keyword;
    //TextView response_tv;
    private RecyclerView recyclerView_cast, recyclerView_crew;
    private LinearLayoutManager linearLayoutManager_cast, linearLayoutManager_crew;
    private CastAdapter movieAdapter_cast;
    private CrewAdapter movieAdapter_crew;

    TextView tv_mvtagline, tv_mvgenre, tv_mvrelease_date, tv_mvruntime, tv_mvoverview,
                tv_mvvotes, tv_mvpopularity, tv_mvproduction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent movie_intent = getIntent();
        final String movie_title = movie_intent.getStringExtra(Constants.in_movie_title);
        setTitle(movie_title);

        movie_keyword = movie_title;

        mAPIService = TMDbAPI.getAPIService();

        FloatingActionButton search_fab = (FloatingActionButton) findViewById(R.id.search_movie);
        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo!=null && networkInfo.isConnected()){
                    get_tmdb_movies(movie_keyword, false);
                } else {
                    Toast.makeText(getApplicationContext(),"Data Connection Failed...",Toast.LENGTH_SHORT).show();
                }
            }
        });

        search_fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo!=null && networkInfo.isConnected()){
                    get_tmdb_movies(movie_keyword, true);
                } else {
                    Toast.makeText(getApplicationContext(),"Data Connection Failed...",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        recyclerView_cast = (RecyclerView) findViewById(R.id.movie_view_cast);
        recyclerView_crew = (RecyclerView) findViewById(R.id.movie_view_crew);

        recyclerView_cast.setHasFixedSize(true);
        recyclerView_crew.setHasFixedSize(true);

        linearLayoutManager_cast = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager_crew = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);


        recyclerView_cast.setLayoutManager(linearLayoutManager_cast);
        recyclerView_crew.setLayoutManager(linearLayoutManager_crew);

        String movies[] = getResources().getStringArray(R.array.mcu_caste);
        final ArrayList movies_list = new ArrayList<String>(Arrays.asList(movies));
        Collections.sort(movies_list);

        ArrayList <CreditsCastTMdb> creditsCastTMdb = new ArrayList<CreditsCastTMdb>();
        ArrayList <CreditsCrewTMdb> creditsCrewTMdb = new ArrayList<CreditsCrewTMdb>();

        for (int i = 0; i < 5; i++){

            creditsCastTMdb.add(new CreditsCastTMdb("Character", "Name", "", 0, i));

            creditsCrewTMdb.add(new CreditsCrewTMdb("Department", "Job", "Name", ""));
        }

        movieAdapter_cast = new CastAdapter(MovieDetailActivity.this, creditsCastTMdb);
        movieAdapter_crew = new CrewAdapter(MovieDetailActivity.this, creditsCrewTMdb);


        recyclerView_cast.setAdapter(movieAdapter_cast);
        recyclerView_crew.setAdapter(movieAdapter_crew);

        tv_mvtagline = (TextView)findViewById(R.id.movie_tagline);
        tv_mvgenre = (TextView)findViewById(R.id.movie_genre);
        tv_mvrelease_date = (TextView)findViewById(R.id.movie_release_date);
        tv_mvruntime = (TextView)findViewById(R.id.movie_runtime);
        tv_mvoverview = (TextView)findViewById(R.id.movie_overview);
        tv_mvvotes = (TextView)findViewById(R.id.movie_votes);
        tv_mvpopularity = (TextView)findViewById(R.id.movie_popularity);
        tv_mvproduction = (TextView)findViewById(R.id.movie_production);


    }

    private void get_tmdb_movie_credits(int movie_id) {

        Call <CreditMovies> movie_credits = mAPIService.get_movie_credits(movie_id, getString(R.string.tmdb_api_key));
        movie_credits.enqueue(new Callback<CreditMovies>() {
            @Override
            public void onResponse(Call<CreditMovies> call, Response<CreditMovies> response) {
                if (response.isSuccessful()){

                    List<CreditCast> movie_cast = response.body().getCast();
                    List<CreditCrew> movie_crew = response.body().getCrew();

                    set_movie_metadata_credits(movie_cast, movie_crew);

                }
            }

            @Override
            public void onFailure(Call<CreditMovies> call, Throwable t) {
                t.printStackTrace();
                Log.e("Request", "Unable to send GET to API. :: "+t.toString());
            }
        });

    }

    private void get_tmdb_movie_data(int movie_id) {

        Call <DetailMovies> movie_details = mAPIService.get_movie_details(movie_id, getString(R.string.tmdb_api_key));
        movie_details.enqueue(new Callback<DetailMovies>() {
            @Override
            public void onResponse(Call<DetailMovies> call, Response<DetailMovies> response) {
                if (response.isSuccessful()){

                    String poster_path = set_movie_metadata(response.body());
                    set_movie_poster(poster_path);
                }
            }

            @Override
            public void onFailure(Call<DetailMovies> call, Throwable t) {
                t.printStackTrace();
                Log.e("Request", "Unable to send GET to API. :: "+t.toString());
            }
        });

    }

    private void set_movie_poster(String poster_path) {

        ImageView movie_poster = (ImageView)findViewById(R.id.movie_poster);

        String tmdb_image_url = Constants.tmdb_image_basse_url + poster_path;

        Picasso mPicasso = Picasso.with(getApplicationContext());               // We might need glide here
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(tmdb_image_url)
                .placeholder(R.drawable.ic_movie_genre_24dp)
                //.resize(50, 50)
                //.centerCrop()
                .into(movie_poster);
    }

    public void get_tmdb_movies(final String movie_title, final boolean is_list) {

        Map<String, String> params = new HashMap<>();
        params.put("api_key", getString(R.string.tmdb_api_key));
        params.put("language", "en-US");
        params.put("query", movie_title);
        params.put("page", String.valueOf(1));
        params.put("include_adult", "false");

        Call <SearchMovies> movies_list = mAPIService.search_movies(params);

        movies_list.enqueue(new Callback <SearchMovies>() {

            @Override
            public void onResponse(Call <SearchMovies> call, Response <SearchMovies> response) {
                if(response.isSuccessful()){

                    ArrayList<MovieTMdb> movie_titles = new ArrayList<MovieTMdb>();
                    ArrayList<SearchMoviesResult> movies_result = response.body().getResults();

                    if (is_list){

                        for (SearchMoviesResult movie:movies_result) {

                            MovieTMdb tMdb = new MovieTMdb(movie.getId(), movie.getOriginalTitle(), movie.getReleaseDate());
                            movie_titles.add(tMdb);
                        }

                        int movie_id = build_movie_dialog(movie_titles);
                        get_tmdb_movie_data(movie_id);
                        get_tmdb_movie_credits(movie_id);

                    } else {

                        SearchMoviesResult first_match_movie = movies_result.get(0);
                        MovieTMdb tMdb = new MovieTMdb(first_match_movie.getId(), first_match_movie.getOriginalTitle(), first_match_movie.getReleaseDate());
                        movie_titles.add(tMdb);

                        int movie_id = first_match_movie.getId();
                        get_tmdb_movie_data(movie_id);
                        get_tmdb_movie_credits(movie_id);
                    }

                }
            }

            @Override
            public void onFailure(Call <SearchMovies> call, Throwable t) {
                t.printStackTrace();
                Log.e("Request", "Unable to send GET to API. :: "+t.toString());
            }
        });
    }

    private String set_movie_metadata(DetailMovies first_match_movie) {

        setTitle(first_match_movie.getOriginalTitle());

        tv_mvtagline.setText(first_match_movie.getTagline());

        List<DetailMoviesGenre> genreList = first_match_movie.getGenres();
        tv_mvgenre.setText("");
        for (DetailMoviesGenre genre: genreList) {
            tv_mvgenre.append(genre.getName()+", ");
        }

        String release_date_str = first_match_movie.getReleaseDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date release_date = sdf.parse(release_date_str);
            Calendar release_date_cal = Calendar.getInstance();
            release_date_cal.setTime(release_date);

            release_date_str = String.valueOf(release_date_cal.get(Calendar.YEAR));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_mvrelease_date.setText(release_date_str);

        tv_mvruntime.setText(String.valueOf(first_match_movie.getRuntime())+" mins");

        tv_mvoverview.setText(first_match_movie.getOverview());

        tv_mvvotes.setText(String.valueOf(first_match_movie.getVoteAverage()));

        Double mv_popularity = first_match_movie.getPopularity()/10 * 100;
        tv_mvpopularity.setText(String.valueOf(mv_popularity.intValue())+" %");

        List<DetailMoviesProductionCompany> productionCompanyList = first_match_movie.getProductionCompanies();
        tv_mvproduction.setText("");
        for (DetailMoviesProductionCompany productionCompany: productionCompanyList) {
            tv_mvproduction.append(productionCompany.getName()+", ");
        }

        if (first_match_movie.getPosterPath() != null) {
            return first_match_movie.getPosterPath();
        } else {
            return first_match_movie.getBackdropPath();
        }
    }

    private void set_movie_metadata_credits(List<CreditCast> movie_cast, List<CreditCrew> movie_crew) {

        int limit = 1;

        ArrayList <CreditsCastTMdb> creditsCastTMdb = new ArrayList<CreditsCastTMdb>();
        ArrayList <CreditsCrewTMdb> creditsCrewTMdb = new ArrayList<CreditsCrewTMdb>();

        for (CreditCast crediCast: movie_cast) {
            if (limit == 16){
                break;
            }

            String cast_character = crediCast.getCharacter();
            String cast_name = crediCast.getName();
            String cast_profile = crediCast.getProfilePath();
            int cast_gender = crediCast.getGender();
            int cast_order = crediCast.getOrder();

            creditsCastTMdb.add(new CreditsCastTMdb(cast_character, cast_name, cast_profile, cast_gender, cast_order));

            limit ++;
        }

        limit = 1;

        for (CreditCrew crediCrew: movie_crew) {
            if (limit == 16){
                break;
            }

            String crew_department = crediCrew.getDepartment();
            String crew_job = crediCrew.getJob();
            String crew_name = crediCrew.getName();
            String crew_profile = crediCrew.getProfilePath();

            String crew_jobs[] = getResources().getStringArray(R.array.tmdb_crew_jobs);
            final ArrayList crew_job_list = new ArrayList<String>(Arrays.asList(crew_jobs));
            if (crew_job_list.contains(crew_job)){

                creditsCrewTMdb.add(new CreditsCrewTMdb(crew_department, crew_job, crew_name, crew_profile));

                limit ++;
            }
        }

        movieAdapter_cast = new CastAdapter(MovieDetailActivity.this, creditsCastTMdb);
        movieAdapter_crew = new CrewAdapter(MovieDetailActivity.this, creditsCrewTMdb);

        recyclerView_cast.setAdapter(movieAdapter_cast);
        recyclerView_crew.setAdapter(movieAdapter_crew);
    }

    private int build_movie_dialog(ArrayList<MovieTMdb> movies_result) {

        final int[] movie_id = new int[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog_view = (View) inflater.inflate(R.layout.movie_list_dialogue_layout, null);
        //ListView listView = (ListView)dialog_view.findViewById(R.id.dialog_movie_list);

        final ArrayAdapter<MovieTMdb> movie_adapter = new MovieTMdbAdapter<MovieTMdb>(this, movies_result);
        builder.setView(dialog_view)
                .setTitle(R.string.movie_list_dialog)
                .setAdapter(movie_adapter, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       movie_id[0] = movie_adapter.getItem(which).movie_id;
                   }
               });
        builder.create().show();
        return movie_id[0];
    }
}
