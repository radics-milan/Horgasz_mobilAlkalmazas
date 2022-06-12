package com.example.horgszmobilalkalmazs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {
    int level;
    String lastGamesDate = null;
    TextView scorestextView;
    ImageView backImageView;
    AdapterScore adapterScore;
    DatabaseScore databaseScore;
    ArrayList<ClassScore> scoreArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        level = getIntent().getIntExtra("level", 1);
        if (getIntent().getStringExtra("date") != null){
            lastGamesDate = getIntent().getStringExtra("date");
        }


        scorestextView = findViewById(R.id.scoresTextView);
        databaseScore = new DatabaseScore(this);
        String scoresText = "Eredmények - " + level + ". szint";
        scorestextView.setText(scoresText);
        backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(o -> finish());

        scoreArrayList = databaseScore.getScoresOnLevel(level);
        adapterScore = new AdapterScore(this, scoreArrayList, lastGamesDate);

        recyclerView = findViewById(R.id.scoreRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapterScore);
    }
}