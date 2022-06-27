package com.example.horgszmobilalkalmazs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FishDetailsActivity extends AppCompatActivity {
    TextView detailsHeaderTextView;
    ImageView fishImageView;
    TextView fishLatinNameTextView;
    TextView fishTypeTextView;
    TextView fishMinimumCatchSizeTextView;
    TextView fishMinimumCatchSizeInCloseSeasonTextView;
    TextView fishCloseSeasonTextView;
    TextView fishDetailsTextView;

    LinearLayout fishMinimumCatchSizeLinearLayout;
    LinearLayout fishMinimumCatchSizeInCloseSeasonLinearLayout;
    LinearLayout fishCloseSeasonLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

        String fishName = getIntent().getStringExtra("fishName");
        if (fishName == null) {
            finish();
        }

        ClassFish fish = new DatabaseFish(this).getHalByNev(fishName);

        findViewById(R.id.backImageView).setOnClickListener(o -> finish());
        detailsHeaderTextView = findViewById(R.id.detailsHeaderTextView);
        fishImageView = findViewById(R.id.fishImageView);
        fishLatinNameTextView = findViewById(R.id.fishLatinNameTextView);
        fishTypeTextView = findViewById(R.id.fishTypeTextView);
        fishMinimumCatchSizeTextView = findViewById(R.id.fishMinimumCatchSizeTextView);
        fishMinimumCatchSizeInCloseSeasonTextView = findViewById(R.id.fishMinimumCatchSizeInCloseSeasonTextView);
        fishCloseSeasonTextView = findViewById(R.id.fishCloseSeasonTextView);
        fishDetailsTextView = findViewById(R.id.fishDetailsTextView);

        fishMinimumCatchSizeLinearLayout = findViewById(R.id.fishMinimumCatchSizeLinearLayout);
        fishMinimumCatchSizeInCloseSeasonLinearLayout = findViewById(R.id.fishMinimumCatchSizeInCloseSeasonLinearLayout);
        fishCloseSeasonLinearLayout = findViewById(R.id.fishCloseSeasonLinearLayout);

        detailsHeaderTextView.setText(fish.getName());
        fishImageView.setImageResource(fish.getImageResourceId());
        fishLatinNameTextView.setText(fish.getLatinName());
        fishTypeTextView.setText(fish.getType());

        if (fish.getMinimumCatchSize() == 0) {
            fishMinimumCatchSizeLinearLayout.setVisibility(View.GONE);
        } else {
            String fishMinimumCatchSizeText = fish.getMinimumCatchSize() + " cm";
            fishMinimumCatchSizeTextView.setText(fishMinimumCatchSizeText);
        }

        if (fish.getMinimumCatchSizeInCloseSeason() == 0) {
            fishMinimumCatchSizeInCloseSeasonLinearLayout.setVisibility(View.GONE);
        } else {
            String fishMinimumCatchSizeInCloseSeasonText = fish.getMinimumCatchSizeInCloseSeason() + " cm";
            fishMinimumCatchSizeInCloseSeasonTextView.setText(fishMinimumCatchSizeInCloseSeasonText);
        }

        if (fish.getCloseSeason() == null) {
            fishCloseSeasonLinearLayout.setVisibility(View.GONE);
        } else {
            fishCloseSeasonTextView.setText(fish.getCloseSeason());
        }

        fishDetailsTextView.setText(fish.getDescription());
    }
}