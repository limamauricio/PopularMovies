package com.limamauricio.popularmovies.proxy;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProxyFactory {

    public static Retrofit retrofit;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    public static Retrofit getInstace(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }
}
