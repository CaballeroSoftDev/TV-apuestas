package com.example.tvapuestas.api;

import com.example.tvapuestas.model.TeamsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TeamService {

    @GET("teams")
    Call<TeamsResponse> getTeamByName(@Query("name") String teamName);

    @GET("teams")
    Call<TeamsResponse> getTeamByNameCountry(
            @Query("name") String name,
            @Query("country") String country
    );

}
