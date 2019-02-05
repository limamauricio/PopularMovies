package com.limamauricio.popularmovies.ui.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Movie;
import com.limamauricio.popularmovies.utils.OnClickListenerEvent;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final List<Movie> movieList;
    private final OnClickListenerEvent onClickListenerEvent;

    public MovieAdapter(List<Movie> movies, OnClickListenerEvent movie) {
        this.movieList = movies;
        this.onClickListenerEvent = movie;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MovieViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.movie_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = this.movieList.get(i);
        movieViewHolder.bind(movie, onClickListenerEvent);

    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}
