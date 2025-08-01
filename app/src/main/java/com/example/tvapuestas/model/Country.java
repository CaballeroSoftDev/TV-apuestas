package com.example.tvapuestas.model;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("name")
    private String name;

    @SerializedName("flag")
    private String flag;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
