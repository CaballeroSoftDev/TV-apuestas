package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Markets implements Serializable {

    @SerializedName("key")
    private String key;
    @SerializedName("last_update")
    private String lastUpdate;

    @SerializedName("outcomes")
    private List<Outcomes> outcomes;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Outcomes> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcomes> outcomes) {
        this.outcomes = outcomes;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
