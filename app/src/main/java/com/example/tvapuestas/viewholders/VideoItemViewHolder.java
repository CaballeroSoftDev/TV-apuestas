package com.example.tvapuestas.viewholders;

import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.R;

public class VideoItemViewHolder extends RecyclerView.ViewHolder {
    public VideoView videoView;
    public TextView textView;

    public VideoItemViewHolder(@NonNull View itemView) {
        super(itemView);
        videoView = itemView.findViewById(R.id.item_video_horizontal);
        textView = itemView.findViewById(R.id.item_text_horizontal);
    }
}