package com.example.tvapuestas.api;

import com.example.tvapuestas.model.Sports;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SportsService {

    @GET("sports")
    Call<List<Sports>> getSportsList();

}
