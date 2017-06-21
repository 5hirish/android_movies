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
import com.alleviate.movies.models.CreditsCrewTMdb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by felix on 9/6/16.
 * Created at Alleviate.
 * shirishkadam.com
 */

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {

    ArrayList<CreditsCrewTMdb> creditsCrewTMdbs;
    Context context;

    public CrewAdapter(Context context, ArrayList<CreditsCrewTMdb> creditsCrewTMdbs) {
        this.creditsCrewTMdbs = creditsCrewTMdbs;
        this.context = context;

    }

    @Override
    public CrewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caste_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CrewAdapter.ViewHolder holder, int position) {

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

        ((TextView) holder.itemView.findViewById(R.id.credit_name)).setText(creditsCrewTMdbs.get(holder.getAdapterPosition()).crew_name);
        ((TextView) holder.itemView.findViewById(R.id.credit_description)).setText(creditsCrewTMdbs.get(holder.getAdapterPosition()).crew_job);


        Picasso.with(context).load(R.drawable.marvel_studios_logo).into(((ImageView) holder.itemView.findViewById(R.id.movie_poster)));

        ((LinearLayout) holder.itemView.findViewById(R.id.mainHolder)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return creditsCrewTMdbs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
