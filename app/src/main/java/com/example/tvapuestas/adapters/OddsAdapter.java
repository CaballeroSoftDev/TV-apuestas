package com.example.tvapuestas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.R;
import com.example.tvapuestas.model.Odds;
import com.example.tvapuestas.utils.DateUtils;
import com.example.tvapuestas.viewholders.OddsViewHolder;

import java.util.List;

public class OddsAdapter extends RecyclerView.Adapter<OddsViewHolder> {

    private List<Odds> oddsList;
    private OnOddsClickListener listener;

    // Interfaz para manejar los clics
    public interface OnOddsClickListener {
        void onOddsClick(Odds odds);
    }

    // Constructor actualizado para recibir el listener
    public OddsAdapter(List<Odds> oddsList, OnOddsClickListener listener) {
        this.oddsList = oddsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OddsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_odds_card, parent, false);
        return new OddsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OddsViewHolder holder, int position) {
        Odds currentOdds = oddsList.get(position);

        holder.textViewDate.setText(DateUtils.convertUtcToLocalDateTime(currentOdds.getCommenceTime()));
        holder.homeTeam.setText(currentOdds.getHomeTeam());
        holder.awayTeam.setText(currentOdds.getAwayTeam());

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOddsClick(currentOdds);
            }
        });
    }

    @Override
    public int getItemCount() {
        return oddsList == null ? 0 : oddsList.size();
    }

    // Método para actualizar la lista de cuotas en el adaptador
    public void updateOddsList(List<Odds> newOddsList) {
        this.oddsList = newOddsList;
        notifyDataSetChanged();
    }

}
