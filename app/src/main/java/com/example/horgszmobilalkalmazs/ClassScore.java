package com.example.horgszmobilalkalmazs;

import androidx.annotation.NonNull;

public class ClassScore {
    String date;
    int points;
    int level;
    String username;
    int positionIndex = 0;

    public ClassScore(String date, int points, int level, String username) {
        this.date = date;
        this.points = points;
        this.level = level;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }

    @NonNull
    @Override
    public String toString() {
        String str;
        if (level < 10){
            float per = (float) ((points / 10.0 ) * 100);
            int p = (int) per;
            str = date + " " + username + " " + points + " / 10 " + "(" + p + "%)" ;
        } else {
            float per = (float) ((points / 82.0 ) * 100);
            int p = (int) per;
            str = date + " " + username + " " + points + " / 82 " + "(" + p + "%)" ;
        }

        return str;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }
}
