package com.alleviate.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by felix on 9/6/16.
 * Created at Alleviate.
 * shirishkadam.com
 */
public class MetaDataAdapter extends RecyclerView.Adapter<MetaDataAdapter.ViewHolder> {

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
    public void onBindViewHolder(MetaDataAdapter.ViewHolder holder, final int position) {
        ((TextView) holder.itemView.findViewById(R.id.name)).setText(mcu_movies.get(position).toString());
        ((RelativeLayout) holder.itemView.findViewById(R.id.relative_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Click..",Toast.LENGTH_SHORT).show();
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
