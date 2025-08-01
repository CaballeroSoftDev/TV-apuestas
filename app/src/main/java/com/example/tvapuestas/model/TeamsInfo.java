package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

public class TeamsInfo {

    @SerializedName("team")
    private Team team;

    @SerializedName("venue")
    private VenueTeam venueTeam;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public VenueTeam getVenueTeam() {
        return venueTeam;
    }

    public void setVenueTeam(VenueTeam venueTeam) {
        this.venueTeam = venueTeam;
    }
}
