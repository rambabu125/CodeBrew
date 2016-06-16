package com.example.rambabu.codebrew;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rambabu on 6/16/2016.
 */
public interface Api {
    @POST("/api/all-friends")
    Call<String> getFriends(@Query("access_token") String accessToken);

}
