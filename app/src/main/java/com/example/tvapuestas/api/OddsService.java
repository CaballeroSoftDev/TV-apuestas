package com.example.tvapuestas.api;

import com.example.tvapuestas.model.Odds;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OddsService {

    @GET("sports/{sport}/odds/?regions=us&markets=h2h&oddsFormat=american&bookmakers=fanduel,betrivers")
    Call<List<Odds>> getOddsList(@Path("sport") String sport);

}
