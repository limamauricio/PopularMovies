package com.limamauricio.popularmovies.proxy;

import com.limamauricio.popularmovies.model.MoviesRequestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Proxy {

    @GET("movie/popular")
    Call<MoviesRequestResponse> getPopularMovies(@Query("page") int page,
                                                 @Query("api_key") String api);


    @GET("movie/top_rated")
    Call<MoviesRequestResponse> getTopMovies(@Query("page") int page,
                                                 @Query("api_key") String api);

}
