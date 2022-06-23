package com.example.horgszmobilalkalmazs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseCatch extends SQLiteOpenHelper {

    String LOG_TAG = DatabaseCatch.class.getName();
    private static final String TABLE_NAME = "CATCHES_TABLE";
    private static final String COL0 = "DATE_OF_CATCH";
    private static final String COL1 = "FISH_NAME";
    private static final String COL2 = "IMAGE";
    private static final String COL3 = "SIZE";
    private static final String COL4 = "WEIGHT";
    private static final String COL5 = "BAIT";
    private static final String COL6 = "LOCATION";

    public DatabaseCatch(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " TEXT PRIMARY KEY, " + COL1 + " TEXT , " + COL2 + " TEXT, " + COL3 + " NUMERIC, " + COL4 + " REAL, " + COL5 + " TEXT, " + COL6 + " TEXT " + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addCatch(ClassCatch classCatch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0, classCatch.getDateOfCatch());
        contentValues.put(COL1, classCatch.getFishName());
        contentValues.put(COL2, classCatch.getImage());
        contentValues.put(COL3, classCatch.getSize());
        contentValues.put(COL4, classCatch.getWeight());
        contentValues.put(COL5, classCatch.getBait());
        contentValues.put(COL6, classCatch.getLocation());

        db.insert(TABLE_NAME, null, contentValues);

    }

    public ArrayList<ClassCatch> getAllCatch() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        ArrayList<ClassCatch> catchArrayList = new ArrayList<>();
        if (data != null && data.moveToFirst()){
            do {
                catchArrayList.add(new ClassCatch(data.getString(0), data.getString(1), data.getString(2), data.getInt(3), data.getFloat(4), data.getString(5), data.getString(6)));
            } while (data.moveToNext());
        }
        assert data != null;
        data.close();
        return catchArrayList;
    }

    public ArrayList<ClassCatch> getFishCatch(String fishName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=" + "'" + fishName + "'";
        Cursor data = db.rawQuery(query, null);
        ArrayList<ClassCatch> catchArrayList = new ArrayList<>();
        if (data != null && data.moveToFirst()){
            do {
                catchArrayList.add(new ClassCatch(data.getString(0), data.getString(1), data.getString(2), data.getInt(3), data.getFloat(4), data.getString(5), data.getString(6)));
            } while (data.moveToNext());
        }
        assert data != null;
        data.close();
        return catchArrayList;
    }

    public void delete(String dateOfCatch) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + "='" + dateOfCatch + "'");
        db.close();
    }
}
