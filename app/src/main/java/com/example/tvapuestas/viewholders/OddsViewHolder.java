package com.example.tvapuestas.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.R;

public class OddsViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewDate;
    public TextView homeTeam;
    public TextView awayTeam;
    public OddsViewHolder(View itemView) {
        super(itemView);
        textViewDate = itemView.findViewById(R.id.textViewDate);
        homeTeam = itemView.findViewById(R.id.textViewTeam1);
        awayTeam = itemView.findViewById(R.id.textViewTeam2);
    }

}
