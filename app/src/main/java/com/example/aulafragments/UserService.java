package com.example.aulafragments;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserService {
    @GET("api/")
    Call<Result> randomUser();
}
