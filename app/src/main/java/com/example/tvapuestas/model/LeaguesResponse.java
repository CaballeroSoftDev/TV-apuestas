package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaguesResponse {

    @SerializedName("response")
    private List<LeaguesInfo> response;

    public List<LeaguesInfo> getResponse() {
        return response;
    }

    public void setResponse(List<LeaguesInfo> response) {
        this.response = response;
    }
}
