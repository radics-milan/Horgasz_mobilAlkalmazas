package com.example.horgszmobilalkalmazs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseScore extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "SCORE_TABLE";
    private static final String COL0 = "DATE";
    private static final String COL1 = "POINTS";
    private static final String COL2 = "LEVEL";
    private static final String COL3 = "USERNAME";

    public DatabaseScore(Context context){
        super(context, TABLE_NAME, null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " TEXT PRIMARY KEY, " + COL1 + " NUMERIC, " + COL2 + " NUMERIC, " + COL3 + " TEXT " + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(ClassScore score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0, score.getDate());
        contentValues.put(COL1, score.getPoints());
        contentValues.put(COL2, score.getLevel());
        contentValues.put(COL3, score.getUsername());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<ClassScore> getScoresOnLevel(int level){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + "=" + level;
        Cursor data = db.rawQuery(query, null);
        ArrayList<ClassScore> scoreArrayList = new ArrayList<>();
        if (data != null && data.moveToFirst()){
            do {
                scoreArrayList.add(new ClassScore(data.getString(0), data.getInt(1), data.getInt(2), data.getString(3)));
            } while (data.moveToNext());
        }
        assert data != null;
        data.close();

        Collections.sort(scoreArrayList, (o1, o2) -> {
            if(o1.getPoints() == o2.getPoints())
                return 0;
            return o1.getPoints() < o2.getPoints() ? 1 : -1;
        });

        for (int i = 0; i < scoreArrayList.size(); i++) {
            scoreArrayList.get(i).setPositionIndex(i+1);
        }

        return scoreArrayList;
    }

    public boolean isLevelCompleted(int level){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE " + COL2 + "=" + level + " AND " + COL1 + "=" + 10;
        Cursor data = db.rawQuery(query, null);
        int count = data.getCount();
        data.close();
        return count != 0;
    }

    public String getLastPlayersUsername() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(DATE) FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        if (data == null || data.getCount() == 0) {
            return null;
        } else {
            data.moveToFirst();
            String maxDate = data.getString(0);
            data.close();

            String query2 = "SELECT USERNAME FROM " + TABLE_NAME + " WHERE " + COL0 + "=" + "'" + maxDate + "'";
            Cursor data2 = db.rawQuery(query2, null);
            if (data2 == null || data2.getCount() == 0) {
                return null;
            } else {
                data2.moveToFirst();
                String username = data2.getString(0);
                data2.close();
                return username;
            }
        }
    }
}
