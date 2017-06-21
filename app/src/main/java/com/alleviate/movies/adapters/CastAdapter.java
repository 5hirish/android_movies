package com.alleviate.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alleviate.movies.MovieDetailActivity;
import com.alleviate.movies.R;
import com.alleviate.movies.helper.Constants;
import com.alleviate.movies.models.CreditsCastTMdb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by felix on 9/6/16.
 * Created at Alleviate.
 * shirishkadam.com
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    ArrayList<CreditsCastTMdb> creditsCastTMdbs;
    Context context;

    public CastAdapter(Context context, ArrayList<CreditsCastTMdb> creditsCastTMdbs) {
        this.creditsCastTMdbs = creditsCastTMdbs;
        this.context = context;

    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caste_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CastAdapter.ViewHolder holder, int position) {

        /*Bitmap poster = BitmapFactory.decodeResource(context.getResources(), R.drawable.marvel_studios_logo);
        if (poster != null && !poster.isRecycled()) {
            Palette.from(poster).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int bgColor = palette.getMutedColor(context.getResources().getColor(R.color.black));
                    ((LinearLayout) holder.itemView.findViewById(R.id.movieTitleHolder)).setBackgroundColor(bgColor);
                }
            });
        }*/

        ((TextView) holder.itemView.findViewById(R.id.credit_name)).setText(creditsCastTMdbs.get(holder.getAdapterPosition()).cast_name);
        ((TextView) holder.itemView.findViewById(R.id.credit_description)).setText(creditsCastTMdbs.get(holder.getAdapterPosition()).cast_character);

        String poster_path = creditsCastTMdbs.get(holder.getAdapterPosition()).cast_profile;
        String tmdb_image_url = Constants.tmdb_image_base_url_profile + poster_path;

        Picasso mPicasso = Picasso.with(context);               // We might need glide here
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(tmdb_image_url)
                .placeholder(R.drawable.ic_movie_genre_24dp)
                //.resize(50, 50)
                //.centerCrop()
                .into((ImageView) holder.itemView.findViewById(R.id.caste_profile));

        //Picasso.with(context)
        //        .load(R.drawable.marvel_studios_logo)
        //        .into(((ImageView) holder.itemView.findViewById(R.id.movie_poster)));

        ((LinearLayout) holder.itemView.findViewById(R.id.mainHolder)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return creditsCastTMdbs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
