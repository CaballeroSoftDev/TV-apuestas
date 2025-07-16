package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamResponse {

    @SerializedName("success")
    private String success;

    private List<Team> result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Team> getResult() {
        return result;
    }

    public void setResult(List<Team> result) {
        this.result = result;
    }

}
