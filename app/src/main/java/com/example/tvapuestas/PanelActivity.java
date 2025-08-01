package com.example.tvapuestas;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tvapuestas.adapters.HorizontalVideoAdapter;
import com.example.tvapuestas.api.ApiFootballClient;
import com.example.tvapuestas.api.TeamService;
import com.example.tvapuestas.model.Bookmakers;
import com.example.tvapuestas.model.TeamsInfo;
import com.example.tvapuestas.model.TeamsResponse;
import com.example.tvapuestas.model.VideoItemModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PanelActivity extends AppCompatActivity implements HorizontalVideoAdapter.OnVideoClickListener {

    private TextView usernameTextView;
    private FrameLayout standard_bottom_sheet;
    private BottomSheetBehavior<FrameLayout> bottomSheetBehavior;
    private Button bottomsheet_button;
    private RecyclerView horizontalRecyclerView;
    private RecyclerView horizontalRecyclerView2;
    private HorizontalVideoAdapter horizontalAdapter;
    private VideoView mainVideoView;

    private TextView textViewHomeTeam;
    private TextView textViewAwayTeam;

    private TextView team1BetButton;
    private TextView team2BetButton;
    private TextView drawBetButton;

    private Bookmakers bookmaker;

    private String leagueName;
    private String countryName;

    private ImageView team1Logo;
    private ImageView team2Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String commenceTime = intent.getStringExtra("commenceTime");
        String homeTeam = intent.getStringExtra("homeTeam");
        String awayTeam = intent.getStringExtra("awayTeam");
        leagueName = intent.getStringExtra("leagueName");
        countryName = intent.getStringExtra("countryName");
        bookmaker = (Bookmakers) intent.getSerializableExtra("bookmaker");

        usernameTextView = findViewById(R.id.user_name);
        bottomsheet_button = findViewById(R.id.bottomsheet_button);
        standard_bottom_sheet = findViewById(R.id.standard_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(standard_bottom_sheet);
        mainVideoView = findViewById(R.id.main_video_view);
        horizontalRecyclerView = findViewById(R.id.horizontal_recycler_view);
        horizontalRecyclerView2 = findViewById(R.id.horizontal_recycler_view2);

        textViewHomeTeam = findViewById(R.id.textViewHomeTeam);
        team1Logo = findViewById(R.id.team1_logo);
        textViewAwayTeam = findViewById(R.id.textViewAwayTeam);
        team2Logo = findViewById(R.id.team2_logo);
        team1BetButton = findViewById(R.id.team1_bet_button);
        team2BetButton = findViewById(R.id.team2_bet_button);
        drawBetButton = findViewById(R.id.draw_bet_button);


        if (username != null) {
            usernameTextView.setText(username);
        } else {
            usernameTextView.setText("Usuario Desconocido");
        }

        if (commenceTime != null && homeTeam != null && awayTeam != null) {
            textViewHomeTeam.setText(homeTeam);
            textViewAwayTeam.setText(awayTeam);

            getTeamLogoUrl(homeTeam, team1Logo, countryName);
            getTeamLogoUrl(awayTeam, team2Logo, countryName);
        } else {
            textViewHomeTeam.setText("Equipo Local Desconocido");
            textViewAwayTeam.setText("Equipo Visitante Desconocido");
        }

        if (bookmaker != null) {
            String team1Price = "$ " + bookmaker.getMarkets().get(0).getOutcomes().get(0).getPrice();
            String team2Price = "$ " + bookmaker.getMarkets().get(0).getOutcomes().get(1).getPrice();
            String drawPrice = "$ " + bookmaker.getMarkets().get(0).getOutcomes().get(2).getPrice();
            team1BetButton.setText(team1Price);
            team2BetButton.setText(team2Price);
            drawBetButton.setText(drawPrice);
        } else {
            team1BetButton.setText("Apuesta Equipo 1");
            team2BetButton.setText("Apuesta Equipo 2");
            drawBetButton.setText("Apuesta Empate");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.panel), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomsheet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        setupHorizontalRecyclerView();

    }

    private void getTeamLogoUrl(String teamName, ImageView teamLogoImageView, String countryName) {

        TeamService teamService = ApiFootballClient.getClient().create(TeamService.class);
        Call<TeamsResponse> call = countryName != null && !countryName.isEmpty()
                ? teamService.getTeamByNameCountry(teamName, countryName) :
                teamService.getTeamByName(teamName);

        call.enqueue(new Callback<TeamsResponse>() {
            @Override
            public void onResponse(Call<TeamsResponse> call, Response<TeamsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TeamsResponse teamResponse = response.body();

                    List<TeamsInfo> teamsList = teamResponse.getResponse();
                    if (!teamsList.isEmpty()) {
                        TeamsInfo team = teamsList.get(0);
                        String logoUrl = team.getTeam().getLogo();

                        if (logoUrl != null && !logoUrl.isEmpty()) {
                            // Usar Glide para cargar la imagen desde URL
                            Glide.with(PanelActivity.this)
                                    .load(logoUrl)
                                    .placeholder(R.drawable.team) // imagen por defecto mientras carga
                                    .error(R.drawable.team) // imagen por defecto si hay error
                                    .into(teamLogoImageView);
                        } else {
                            Toast.makeText(PanelActivity.this, "Logo no disponible para " + teamName, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(PanelActivity.this, "Equipo no encontrado: " + teamName, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PanelActivity.this, "Error al cargar el logo del equipo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeamsResponse> call, Throwable t) {
                Toast.makeText(PanelActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupHorizontalRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView2.setLayoutManager(layoutManager2);

        List<VideoItemModel> itemList = createVideoItemList();

        horizontalAdapter = new HorizontalVideoAdapter(itemList, this);
        horizontalRecyclerView.setAdapter(horizontalAdapter);
        horizontalRecyclerView2.setAdapter(horizontalAdapter);
    }

    @Override
    public void onVideoClick(VideoItemModel video) {
        // Reproducir el video seleccionado en el VideoView principal
        playVideoInMainView(video.getVideoResourceId());
    }

    private void playVideoInMainView(int videoResourceId) {
        // Configurar el video con controles
        String path = "android.resource://" + getPackageName() + "/" + videoResourceId;
        Uri uriVideo = Uri.parse(path);
        mainVideoView.setVideoURI(uriVideo);

        // Agregar controles de media
        MediaController mediaController = new MediaController(PanelActivity.this);
        mediaController.setAnchorView(mainVideoView);
        mainVideoView.setMediaController(mediaController);

        // Forzar la preparación y reproducción
        mainVideoView.requestFocus();

        mainVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mainVideoView.start();
            }
        });

        mainVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mainVideoView.start();
            }
        });
    }

    private List<VideoItemModel> createVideoItemList() {
        List<VideoItemModel> itemList = new ArrayList<>();
        itemList.add(new VideoItemModel("Video 1", R.raw.video5));
        itemList.add(new VideoItemModel("Video 2", R.raw.video1));
        itemList.add(new VideoItemModel("Video 3", R.raw.video2));
        itemList.add(new VideoItemModel("Video 4", R.raw.video3));
        itemList.add(new VideoItemModel("Video 5", R.raw.video4));
        itemList.add(new VideoItemModel("Video 6", R.raw.video5));
        itemList.add(new VideoItemModel("Video 1", R.raw.video5));
        itemList.add(new VideoItemModel("Video 2", R.raw.video1));
        itemList.add(new VideoItemModel("Video 3", R.raw.video2));
        itemList.add(new VideoItemModel("Video 4", R.raw.video3));
        itemList.add(new VideoItemModel("Video 5", R.raw.video4));
        itemList.add(new VideoItemModel("Video 6", R.raw.video5));

        return itemList;
    }

}