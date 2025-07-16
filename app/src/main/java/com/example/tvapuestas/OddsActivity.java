package com.example.tvapuestas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapuestas.adapters.OddsAdapter;
import com.example.tvapuestas.api.ApiClient;
import com.example.tvapuestas.api.OddsService;
import com.example.tvapuestas.model.Bookmakers;
import com.example.tvapuestas.model.Odds;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class OddsActivity extends AppCompatActivity implements OddsAdapter.OnOddsClickListener {

    private RecyclerView recyclerViewOdds;
    private OddsAdapter oddsAdapter;
    private List<Odds> oddsDataList;
    private String username;
    private String sportKey;
    private TextView textViewSportTitleHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_odds);

        Intent intent = getIntent();
        sportKey = intent.getStringExtra("sportKey");
        String description = intent.getStringExtra("description");
        username = intent.getStringExtra("username");

        textViewSportTitleHeader = findViewById(R.id.textViewSportTitleHeader);

        if (sportKey != null && description != null) {
            textViewSportTitleHeader.setText(description);
        } else {
            textViewSportTitleHeader.setText("Desconocido");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewOdds = findViewById(R.id.recyclerViewOdds);
        oddsDataList = new ArrayList<>();

        // Configura el RecyclerView
        recyclerViewOdds.setLayoutManager(new LinearLayoutManager(this));
        oddsAdapter = new OddsAdapter(oddsDataList, this);
        recyclerViewOdds.setAdapter(oddsAdapter);

        loadOddsData();

    }

    @Override
    public void onOddsClick(Odds odds) {
        // Obtener la lista de Bookmakers del objeto Odds seleccionado
        List<Bookmakers> bookmakersList = odds.getBookmakers();

        if (bookmakersList == null || bookmakersList.isEmpty()) {
            Toast.makeText(this, "No hay bookmakers disponibles para esta apuesta.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un array de Strings para mostrar los nombres de los bookmakers en el diálogo
        String[] bookmakerNames = new String[bookmakersList.size()];
        for (int i = 0; i < bookmakersList.size(); i++) {
            bookmakerNames[i] = bookmakersList.get(i).getTitle(); // Asumiendo que Bookmakers tiene un método getTitle() o similar
        }

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(OddsActivity.this);
        builder.setTitle("Selecciona una Casa de Apuestas");
        builder.setItems(bookmakerNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 'which' es el índice del item seleccionado
                Bookmakers selectedBookmaker = bookmakersList.get(which);

                // Navegar a PanelActivity con el bookmaker seleccionado
                Intent intent = new Intent(OddsActivity.this, PanelActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("commenceTime", odds.getCommenceTime());
                intent.putExtra("homeTeam", odds.getHomeTeam());
                intent.putExtra("awayTeam", odds.getAwayTeam());
                intent.putExtra("bookmaker", selectedBookmaker); // Pasar el objeto Bookmakers
                startActivity(intent);
            }
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void loadOddsData() {
        OddsService oddsService = ApiClient.getClient().create(OddsService.class);

        Call<List<Odds>> call = oddsService.getOddsList(sportKey);

        call.enqueue(new Callback<List<Odds>>() {
            @Override
            public void onResponse(Call<List<Odds>> call, retrofit2.Response<List<Odds>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(OddsActivity.this, "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
                    oddsDataList.clear();
                    oddsDataList.addAll(response.body());
                    oddsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(OddsActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Odds>> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
                Toast.makeText(OddsActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}