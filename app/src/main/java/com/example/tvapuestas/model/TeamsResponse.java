package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamsResponse {

    @SerializedName("response")
    private List<TeamsInfo> response;

    public List<TeamsInfo> getResponse() {
        return response;
    }

    public void setResponse(List<TeamsInfo> response) {
        this.response = response;
    }
}
