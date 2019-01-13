package com.limamauricio.popularmovies.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limamauricio.popularmovies.R;
import com.limamauricio.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private static String IMAGE_URL = "http://image.tmdb.org/t/p/w780";

    @BindView(R.id.movieDetailsTitle)
    TextView movieDetailsTitle;

    @BindView(R.id.movieDetailsImage)
    ImageView movieDetailsPoster;

    @BindView(R.id.movieDetailsOverview)
    TextView movieDetailsOverview;

    @BindView(R.id.movieDetailsReleaseDate)
    TextView releaseDate;

    @BindView(R.id.movieDetailsRating)
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Movie movie = (Movie) bundle.getSerializable("movie");

        prepareDetailsLayout(movie);
    }

    private void prepareDetailsLayout(Movie movieDetail){


        Glide.with(this).load(IMAGE_URL+movieDetail.getImgPath()).into(movieDetailsPoster);
        movieDetailsTitle.setText(movieDetail.getTitle());
        movieDetailsOverview.setText(movieDetail.getOverview());
        releaseDate.setText(movieDetail.getReleaseDate());
        ratingBar.setRating(movieDetail.getVoteAverage());



    }
}
