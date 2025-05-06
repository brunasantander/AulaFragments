package com.example.aulafragments;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder().baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create()).build();
    }

    public UserService getRandomUser() {
        return this.retrofit.create(UserService.class);
    }
}