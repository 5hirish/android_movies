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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by felix on 9/6/16.
 * Created at Alleviate.
 * shirishkadam.com
 */

public class CasteAdapter extends RecyclerView.Adapter<CasteAdapter.ViewHolder> {

    ArrayList mcu_movies;
    Context context;

    public CasteAdapter(Context context, ArrayList mcu_movies) {
        this.mcu_movies = mcu_movies;
        this.context = context;

    }

    @Override
    public CasteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caste_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CasteAdapter.ViewHolder holder, int position) {

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

        ((TextView) holder.itemView.findViewById(R.id.movie_title)).setText(mcu_movies.get(position).toString());

        Picasso.with(context).load(R.drawable.marvel_studios_logo).into(((ImageView) holder.itemView.findViewById(R.id.movie_poster)));

        ((LinearLayout) holder.itemView.findViewById(R.id.mainHolder)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, MovieDetailActivity.class);
                in.putExtra(Constants.in_movie_title, mcu_movies.get(holder.getAdapterPosition()).toString());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcu_movies.size();
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(String movie) {
        int position = mcu_movies.indexOf(movie);
        mcu_movies.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void update(String movie) {
        int position = mcu_movies.indexOf(movie);
        mcu_movies.set(position,"Movie Liked");
        notifyItemChanged(position);
    }

    public void insert(int position) {
        mcu_movies.add(position, "New Movie");
        notifyItemInserted(position);
        //getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
