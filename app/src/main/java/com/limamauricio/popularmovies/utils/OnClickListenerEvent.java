package com.limamauricio.popularmovies.utils;

import com.limamauricio.popularmovies.model.Movie;
import com.limamauricio.popularmovies.model.Trailer;

public interface OnClickListenerEvent {

    void onMovieClick(Movie movie);

    void onTrailerClick(Trailer trailer);
}
