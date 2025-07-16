package com.example.tvapuestas.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.R;

public class SportViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewDescription;

    public SportViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
    }
}
