package com.example.tvapuestas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
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

import com.bumptech.glide.Glide;
import com.example.tvapuestas.adapters.OddsAdapter;
import com.example.tvapuestas.api.ApiClient;
import com.example.tvapuestas.api.ApiFootballClient;
import com.example.tvapuestas.api.LeagueService;
import com.example.tvapuestas.api.OddsService;
import com.example.tvapuestas.model.Bookmakers;
import com.example.tvapuestas.model.LeaguesInfo;
import com.example.tvapuestas.model.LeaguesResponse;
import com.example.tvapuestas.model.Odds;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OddsActivity extends AppCompatActivity implements OddsAdapter.OnOddsClickListener {

    private RecyclerView recyclerViewOdds;
    private OddsAdapter oddsAdapter;
    private List<Odds> oddsDataList;
    private String username;
    private String sportKey;

    private String leagueName;
    private String countryName;

    private TextView textViewSportTitleHeader;
    private ImageView imageViewLeagueIcon;
    private ImageView imageViewCountryIcon;

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
        imageViewLeagueIcon = findViewById(R.id.imageViewLeagueIcon);
        imageViewCountryIcon = findViewById(R.id.imageViewCountryIcon);

        if (sportKey != null && description != null) {
            textViewSportTitleHeader.setText(description);
            String[] parts = description.split("/");
            String[] partsLeague = parts[1].split("-");
            leagueName = partsLeague[0].trim();
            countryName = partsLeague.length > 1 ? partsLeague[1].trim() : null;

            loadLeague(leagueName, countryName);
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
                intent.putExtra("leagueName", leagueName);
                intent.putExtra("countryName", countryName);
                intent.putExtra("bookmaker", selectedBookmaker); // Pasar el objeto Bookmakers
                startActivity(intent);
            }
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadLeague(String name, String country) {
        LeagueService leagueService = ApiFootballClient.getClient().create(LeagueService.class);

        Call<LeaguesResponse> call = country != null && !country.isEmpty() ?
                leagueService.getLeagueByNameCountry(name, country) :
                leagueService.getLeagueByName(name);

        call.enqueue(new Callback<LeaguesResponse>() {

            @Override
            public void onResponse(Call<LeaguesResponse> call, Response<LeaguesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LeaguesResponse leaguesResponse = response.body();

                    List<LeaguesInfo> leaguesList = leaguesResponse.getResponse();
                    if (!leaguesList.isEmpty()) {
                        LeaguesInfo leagues = leaguesList.get(0);
                        String logoUrl = leagues.getLeague().getLogo();
                        String countryLogoUrl = leagues.getCountry().getFlag();

                        if (logoUrl != null && !logoUrl.isEmpty()) {
                            // Usar Glide para cargar la imagen desde URL
                            Glide.with(OddsActivity.this)
                                    .load(logoUrl)
                                    .placeholder(R.drawable.league) // imagen por defecto mientras carga
                                    .error(R.drawable.league) // imagen por defecto si hay error
                                    .into(imageViewLeagueIcon);
                        } else {
                            Toast.makeText(OddsActivity.this, "Logo no disponible para " + name, Toast.LENGTH_SHORT).show();
                        }

                        if (countryLogoUrl != null && !countryLogoUrl.isEmpty()) {
                            Uri uri = Uri.parse(countryLogoUrl);
                            GlideToVectorYou.justLoadImage(OddsActivity.this, uri, imageViewCountryIcon);
                        } else {
                            Toast.makeText(OddsActivity.this, "Logo no disponible para " + country, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(OddsActivity.this, "Liga no encontrada: " + name, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OddsActivity.this, "Error al cargar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LeaguesResponse> call, Throwable t) {
                Toast.makeText(OddsActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
                Toast.makeText(OddsActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}