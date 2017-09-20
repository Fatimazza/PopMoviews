package com.fatimazza.popmoviews.popmoviews.network;


import com.fatimazza.popmoviews.popmoviews.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper retrofitHelper;

    private Retrofit mRetrofit;

    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
            .baseUrl(Constant.MOVIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

}