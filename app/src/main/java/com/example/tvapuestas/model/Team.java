package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("team_key")
    private String teamKey;
    @SerializedName("team_name")
    private String teamName;
    @SerializedName("team_logo")
    private String teamLogo;

    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }
}
