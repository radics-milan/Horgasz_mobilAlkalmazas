package com.example.horgszmobilalkalmazs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity {
    int level;
    String lastGamesDate = null;
    TextView scorestextView;
    ImageView backImageView;
    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        level = getIntent().getIntExtra("level", 1);
        if (getIntent().getStringExtra("date") != null){
            lastGamesDate = getIntent().getStringExtra("date");
        }


        scorestextView = findViewById(R.id.scoresTextView);
        String scoresText = "Eredmények - " + level + ". szint";
        scorestextView.setText(scoresText);
        backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(o -> finish());

        scoreTextView = findViewById(R.id.scoreTextView);

        scoreTextView.setText(lastGamesDate);

    }
}