package com.example.tvapuestas.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.VideoView;

public class VideoUtils {

    public static void setupVideoView(VideoView videoView, Context context, int videoResourceId) {
        String path = "android.resource://" + context.getPackageName() + "/" + videoResourceId;
        Uri videoUri = Uri.parse(path);
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            //videoView.start();
        });
    }

}
