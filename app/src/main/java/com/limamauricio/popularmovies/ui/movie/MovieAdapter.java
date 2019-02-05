package com.limamauricio.popularmovies.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Movie;
import com.limamauricio.popularmovies.utils.MovieOnClickListener;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final List<Movie> movieList;
    private final MovieOnClickListener movieOnClickListener;

    public MovieAdapter(List<Movie> movies, MovieOnClickListener movie) {
        this.movieList = movies;
        this.movieOnClickListener = movie;

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
        movieViewHolder.bind(movie, movieOnClickListener);

    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}
