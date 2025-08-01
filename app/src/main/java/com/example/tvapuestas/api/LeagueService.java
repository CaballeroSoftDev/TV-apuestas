package com.example.tvapuestas.api;

import com.example.tvapuestas.model.LeaguesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LeagueService {

    @GET("leagues")
    Call<LeaguesResponse> getLeagueByName(@Query("name") String name);

    @GET("leagues")
    Call<LeaguesResponse> getLeagueByNameCountry(
            @Query("name") String name,
            @Query("country") String country
    );

}
