package com.fatimazza.popmoviews.popmoviews.network;


import com.fatimazza.popmoviews.popmoviews.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper retrofitHelper;

    private TheMoviesServices mMoviesServices;

    private RetrofitHelper() {
        Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(Constant.MOVIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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

}
