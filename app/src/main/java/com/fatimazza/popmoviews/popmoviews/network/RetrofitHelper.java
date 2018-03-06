package com.fatimazza.popmoviews.popmoviews.network;


import com.fatimazza.popmoviews.popmoviews.utils.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper retrofitHelper;

    private TheMoviesServices mMoviesServices;

    private RetrofitHelper() {
        Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(Constant.MOVIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(setupLoggingInterceptor().build())
            .build();
        mMoviesServices = mRetrofit.create(TheMoviesServices.class);
    }

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    public TheMoviesServices getMoviesServices() {
        return mMoviesServices;
    }

    private OkHttpClient.Builder setupLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return httpClient;
    }

}
