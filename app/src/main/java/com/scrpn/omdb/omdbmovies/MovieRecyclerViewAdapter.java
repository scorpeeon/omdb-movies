package com.scrpn.omdb.omdbmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scrpn.omdb.omdbmovies.network.model.Movie;

import java.util.List;

public class MovieRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnMovieSelectedListener listener;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Movie item = (Movie) view.getTag();
            listener.onMovieSelected(item.getImdbID());
        }
    };

    MovieRecyclerViewAdapter(List<Movie> movies, OnMovieSelectedListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(movies.get(position).getTitle());

        holder.itemView.setTag(movies.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
