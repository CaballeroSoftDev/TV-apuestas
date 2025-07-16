package com.example.tvapuestas.api;

import com.example.tvapuestas.model.TeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TeamService {

    @GET("football/?&met=Teams")
    Call<TeamResponse> getTeamByName(@Query("teamName") String teamName);

}
