package com.example.rambabu.codebrew;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rambabu on 6/16/2016.
 */
public class RetrofitHandler {
    private static RetrofitHandler ourInstance = new RetrofitHandler();

    public static RetrofitHandler getInstance() {
        return ourInstance;
    }

    private Retrofit ipApiRetrofit = new Retrofit.Builder()
            .baseUrl(Contact.BASE_URL)
            .addConverterFactory(StringConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Api ipApiService = ipApiRetrofit.create(Api.class);

    private RetrofitHandler() {
    }

    public Call<String> getFriends(String token) {
        return ipApiService.getFriends(token);
    }
}

