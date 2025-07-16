package com.example.tvapuestas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.adapters.SportsAdapter;
import com.example.tvapuestas.api.ApiClient;
import com.example.tvapuestas.api.SportsService;
import com.example.tvapuestas.model.Sports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsActivity extends AppCompatActivity implements SportsAdapter.OnSportClickListener {
    private RecyclerView recyclerViewSports;
    private SportsAdapter sportsAdapter;
    private List<Sports> sportsDataList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sports);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewSports = findViewById(R.id.recyclerViewSports);
        sportsDataList = new ArrayList<>();
        // Configura el RecyclerView
        recyclerViewSports.setLayoutManager(new LinearLayoutManager(this));
        sportsAdapter = new SportsAdapter(sportsDataList, this);
        recyclerViewSports.setAdapter(sportsAdapter);

        loadSportsData();

    }

    // Implementación del método de la interfaz
    @Override
    public void onSportClick(Sports sport) {
        // Navegar a la nueva actividad con los datos del deporte seleccionado
        Intent intent = new Intent(SportsActivity.this, OddsActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("sportKey", sport.getKey());
        intent.putExtra("description", sport.getDescription() + " / " + sport.getTitle());
        startActivity(intent);
    }

    private void loadSportsData()   {
        SportsService sportsService = ApiClient.getClient().create(SportsService.class);

        Call<List<Sports>> call = sportsService.getSportsList();
        call.enqueue(new Callback<List<Sports>>() {
            @Override
            public void onResponse(Call<List<Sports>> call, Response<List<Sports>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(SportsActivity.this, "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
                    sportsDataList.clear();
                    for (Sports sport : response.body()) {
                        if (sport.getGroup().equals("Soccer")) {
                            sportsDataList.add(sport);
                        }
                    }
                    // Ordenar alfabéticamente por descripción
                    Collections.sort(sportsDataList, (sport1, sport2) ->
                            sport1.getTitle().compareToIgnoreCase(sport2.getTitle()));
                    sportsAdapter.updateSportsList(sportsDataList);
                } else {
                    Toast.makeText(SportsActivity.this, "Error en la respuesta: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sports>> call, Throwable t) {
                Toast.makeText(SportsActivity.this, "Fallo en la llamada: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}