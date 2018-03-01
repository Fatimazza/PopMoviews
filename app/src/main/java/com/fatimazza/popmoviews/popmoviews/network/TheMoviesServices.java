package com.fatimazza.popmoviews.popmoviews.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TheMoviesServices {

    @GET("movie/popular")
    Call<BaseListDao<MovieDetailDao>> fetchPopularMovies(
        @Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<BaseListDao<MovieDetailDao>> fetchTopRatedMovies(
        @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<BaseListDao<MovieReviewDao>> getMovieReview(
        @Path("movie_id") long movieId, @Query("api_key") String apiKey);

}
