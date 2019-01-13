package com.limamauricio.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Movie implements Serializable {

    @Getter
    @Setter
    @SerializedName("id")
    private int id;

    @Getter
    @Setter
    @SerializedName("title")
    private String title;

    @Getter
    @Setter
    @SerializedName("poster_path")
    private String imgPath;

    @Getter
    @Setter
    @SerializedName("overview")
    private String overview;

    @Getter
    @Setter
    @SerializedName("vote_average")
    private double voteAverage;

    @Getter
    @Setter
    @SerializedName("release_date")
    private String releaseDate;


}
