package com.example.aulafragments;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("api/")
    Call<Result> randomUser();

    @GET("api/")
    Call<Result> randomUserFiltered(
            @Query("gender") String gender,
            @Query("nat") String nationality
    );
}
