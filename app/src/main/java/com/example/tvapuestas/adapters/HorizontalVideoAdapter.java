package com.example.tvapuestas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.R;
import com.example.tvapuestas.model.VideoItemModel;
import com.example.tvapuestas.utils.VideoUtils;
import com.example.tvapuestas.viewholders.VideoItemViewHolder;

import java.util.List;

public class HorizontalVideoAdapter extends RecyclerView.Adapter<VideoItemViewHolder> {

    private List<VideoItemModel> itemList;
    private OnVideoClickListener listener;

    public interface OnVideoClickListener {
        void onVideoClick(VideoItemModel video);
    }

    public HorizontalVideoAdapter(List<VideoItemModel> itemList, OnVideoClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_horizontal_layout, parent, false);
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoItemViewHolder holder, int position) {
        VideoItemModel item = itemList.get(position);
        holder.textView.setText(item.getText());

        VideoUtils.setupVideoView(holder.videoView,
                holder.itemView.getContext(),
                item.getVideoResourceId());

        // Agregar clic al elemento completo
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVideoClick(item);
            }
        });

        // Mantener también el clic en el video
        holder.videoView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVideoClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}