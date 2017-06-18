package com.alleviate.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;

/**
 * Created by felix on 9/6/16.
 * Created at Alleviate.
 * shirishkadam.com
 */

public class MetaDataAdapter extends RecyclerView.Adapter<MetaDataAdapter.ViewHolder> implements INameableAdapter {

    ArrayList mcu_movies;
    Context context;

    public MetaDataAdapter(Context context, ArrayList mcu_movies) {
        this.mcu_movies = mcu_movies;
        this.context = context;

    }

    @Override
    public MetaDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.metadata_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MetaDataAdapter.ViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.name)).setText(mcu_movies.get(position).toString());
        ((RelativeLayout) holder.itemView.findViewById(R.id.relative_layout)).setOnClickListener(new View.OnClickListener() {
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

    @Override
    public Character getCharacterForElement(int element) {
        Character character = mcu_movies.get(element).toString().charAt(0);
        if(Character.isDigit(character)){
            character = '#';
        }
        return character;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
