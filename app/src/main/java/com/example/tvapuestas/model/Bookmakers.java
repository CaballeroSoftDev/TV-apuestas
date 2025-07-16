package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bookmakers implements Serializable {

    @SerializedName("key")
    private String key;
    @SerializedName("title")
    private String title;
    @SerializedName("last_update")
    private String lastUpdate;

    @SerializedName("markets")
    private List<Markets> markets;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Markets> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Markets> markets) {
        this.markets = markets;
    }
}
