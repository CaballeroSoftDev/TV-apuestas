package com.example.tvapuestas.model;

public class VideoItemModel {
    private String text;
    private int videoResourceId;

    public VideoItemModel(String text, int videoResourceId) {
        this.text = text;
        this.videoResourceId = videoResourceId;
    }

    public String getText() {
        return text;
    }

    public int getVideoResourceId() {
        return videoResourceId;
    }
}