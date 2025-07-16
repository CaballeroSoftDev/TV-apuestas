package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Odds implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("sport_key")
    private String sportKey;
    @SerializedName("sport_title")
    private String sportTitle;
    @SerializedName("commence_time")
    private String commenceTime;
    @SerializedName("home_team")
    private String homeTeam;
    @SerializedName("away_team")
    private String awayTeam;
    @SerializedName("bookmakers")
    private List<Bookmakers> bookmakers; // Casas de apuestas

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportKey() {
        return sportKey;
    }

    public void setSportKey(String sportKey) {
        this.sportKey = sportKey;
    }

    public String getSportTitle() {
        return sportTitle;
    }

    public void setSportTitle(String sportTitle) {
        this.sportTitle = sportTitle;
    }

    public String getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(String commenceTime) {
        this.commenceTime = commenceTime;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public List<Bookmakers> getBookmakers() {
        return bookmakers;
    }

    public void setBookmakers(List<Bookmakers> bookmakers) {
        this.bookmakers = bookmakers;
    }
}
