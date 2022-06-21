package com.example.horgszmobilalkalmazs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterScore extends RecyclerView.Adapter<AdapterScore.ViewHolder> {
    Context context;
    ArrayList<ClassScore> scoreArrayList;
    String lastGamesDate;

    AdapterScore(Context context, ArrayList<ClassScore> scoreArrayList, String lastGamesDate) {
        this.context = context;
        this.scoreArrayList = scoreArrayList;
        this.lastGamesDate = lastGamesDate;
    }

    @NonNull
    @Override
    public AdapterScore.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.score_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterScore.ViewHolder holder, int position) {
        ClassScore score = scoreArrayList.get(position);
        holder.bindTo(score);
    }

    @Override
    public int getItemCount() {
        return scoreArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView scoreNumberTextView;
        TextView scoreTextTextView;
        HorizontalScrollView scoreHorizontalScrollView;

        public ViewHolder(View itemView) {
            super(itemView);
            scoreNumberTextView = itemView.findViewById(R.id.scoreNumberTextView);
            scoreTextTextView = itemView.findViewById(R.id.scoreTextTextView);
            scoreHorizontalScrollView = itemView.findViewById(R.id.scoreHorizontalScrollView);
        }

        public void bindTo(ClassScore score) {
            if (score.getPositionIndex() % 2 == 0){
                scoreHorizontalScrollView.setBackgroundColor(itemView.getResources().getColor(R.color.light_orange));
            } else {
                scoreHorizontalScrollView.setBackgroundColor(itemView.getResources().getColor(R.color.yellow));
            }
            if (score.getDate().equals(lastGamesDate)) {
                scoreHorizontalScrollView.setBackgroundColor(itemView.getResources().getColor(R.color.red));
            }
            String scoreNumberText = score.getPositionIndex() + ".";
            scoreNumberTextView.setText(scoreNumberText);
            scoreTextTextView.setText(score.toString());

        }
    }
}
