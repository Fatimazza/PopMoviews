package com.fatimazza.popmoviews.popmoviews.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TheMoviesServices {

    @GET("popular")
    Call<MovieDao> fetchPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieDao> fetchTopRatedMovies(@Query("api_key") String apiKey);

}
