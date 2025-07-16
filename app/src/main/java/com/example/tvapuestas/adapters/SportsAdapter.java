package com.example.tvapuestas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.R;
import com.example.tvapuestas.model.Sports;
import com.example.tvapuestas.viewholders.SportViewHolder;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportViewHolder> {

    private List<Sports> sportsList;
    private OnSportClickListener listener;

    // Interfaz para manejar los clics
    public interface OnSportClickListener {
        void onSportClick(Sports sport);
    }

    // Constructor actualizado para recibir el listener
    public SportsAdapter(List<Sports> sportsList, OnSportClickListener listener) {
        this.sportsList = sportsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sport_card, parent, false);
        return new SportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        Sports currentSport = sportsList.get(position);
        holder.textViewTitle.setText(currentSport.getTitle());
        holder.textViewDescription.setText(currentSport.getDescription());

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSportClick(currentSport);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportsList == null ? 0 : sportsList.size();
    }

    // Método para actualizar la lista de deportes en el adaptador
    public void updateSportsList(List<Sports> newSportsList) {
        this.sportsList = newSportsList;
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }
}