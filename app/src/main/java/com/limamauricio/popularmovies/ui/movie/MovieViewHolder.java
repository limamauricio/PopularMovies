package com.limamauricio.popularmovies.ui.movie;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Movie;
import com.limamauricio.popularmovies.utils.OnClickListenerEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_movie)
    ImageView imgMovie;

    @BindView(R.id.movie_item)
    CardView movieItem;

    private final String IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void bind(final Movie movie, final OnClickListenerEvent onClickListenerEvent){

        int screenFactor = 2;
        if(itemView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            screenFactor = 4;
        }

        movieItem.setLayoutParams(new ViewGroup.LayoutParams(
                getScreenWidth()/screenFactor,
                getMeasuredPosterHeight(getScreenWidth()/screenFactor)));
        Glide.with(itemView)
                .load(IMAGE_URL +movie.getImgPath())
                .into(imgMovie);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerEvent.onMovieClick(movie);
            }
        });
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    private int getMeasuredPosterHeight(int width) {
        return (int) (width * 1.5f);
    }
}
