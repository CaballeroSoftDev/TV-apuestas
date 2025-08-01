package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

public class LeaguesInfo {

    @SerializedName("league")
    private League league;

    @SerializedName("country")
    private Country country;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
