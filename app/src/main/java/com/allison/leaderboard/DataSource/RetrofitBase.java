package com.allison.leaderboard.DataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private static RetrofitBase instance = null;
    public static final String GET_BASE_URL = "https://gadsapi.herokuapp.com/api/";
    public static final String POST_BASE_URL = "https://docs.google.com/forms/d/e/";

    public static RetrofitBase getInstance() {
        if (instance == null) {
            instance = new RetrofitBase();
        }

        return instance;
    }

    public Retrofit buildRetrofit(String BaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
