package com.example.horgszmobilalkalmazs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClassHal {
    private String nev;
    private String latinNev;
    private int imageResourceId;
    private String tipus;
    private String tilalmiIdoszak;
    private int legkisebbKifoghatoMeret;
    private int legkisebbKifoghatoMeretTilalmiIdoszakban;
    private String leiras;

    public ClassHal(String nev, String latinNev, int imageResourceId, String tipus, String tilalmiIdoszak, int legkisebbKifoghatoMeret, int legkisebbKifoghatoMeretTilalmiIdoszakban, String leiras) {
        this.nev = nev;
        this.latinNev = latinNev;
        this.imageResourceId = imageResourceId;
        this.tipus = tipus;
        this.tilalmiIdoszak = tilalmiIdoszak;
        this.legkisebbKifoghatoMeret = legkisebbKifoghatoMeret;
        this.legkisebbKifoghatoMeretTilalmiIdoszakban = legkisebbKifoghatoMeretTilalmiIdoszakban;
        this.leiras = leiras;
    }

    public ClassHal(){
        //Firestore letöltéshez kell üres konstruktor
    }

    public String getNev() {
        return nev;
    }

    public String getLatinNev() {
        return latinNev;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getTipus() {
        return tipus;
    }

    public String getTilalmiIdoszak() {
        return tilalmiIdoszak;
    }

    public int getLegkisebbKifoghatoMeret() {
        return legkisebbKifoghatoMeret;
    }

    public int getLegkisebbKifoghatoMeretTilalmiIdoszakban() {
        return legkisebbKifoghatoMeretTilalmiIdoszakban;
    }

    public String getLeiras() {
        return leiras;
    }

    public boolean isTilalmiIdoszakToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.", Locale.getDefault());
        String today = sdf.format(new Date());

        String[] currentDateParts = today.split("\\.");
        int currentMonth = Integer.parseInt(currentDateParts[0]);
        int currentDay = Integer.parseInt(currentDateParts[1]);

        if (getTilalmiIdoszak() != null) {
            String[] tilalmiIdoszakDateParts = getTilalmiIdoszak().split("-");
            String startDate = tilalmiIdoszakDateParts[0];
            String endDate = tilalmiIdoszakDateParts[1];

            String[] startDateParts = startDate.split("\\.");
            int startMonth = Integer.parseInt(startDateParts[0]);
            int startDay = Integer.parseInt(startDateParts[1]);

            String[] endDateParts = endDate.split("\\.");
            int endMonth = Integer.parseInt(endDateParts[0]);
            int endDay = Integer.parseInt(endDateParts[1]);

            if (startMonth <= endMonth) {
                return (startMonth == currentMonth && currentDay >= startDay) || (endMonth == currentMonth && currentDay <= endDay) || (startMonth < currentMonth && currentMonth < endMonth);
            } else {
                return (startMonth == currentMonth && currentDay >= startDay) || (endMonth == currentMonth && currentDay <= endDay) || currentMonth > startMonth || currentMonth < endMonth;
            }
        } else {
            return false;
        }
    }

    public boolean isBiggerThanLegkisebbKifoghatoMeret(int size){
        return size >= getLegkisebbKifoghatoMeret();
    }

    public boolean isBiggerThanLegkisebbKifoghatoMeretTilalmiIdoszakban(int size){
        return size >= getLegkisebbKifoghatoMeretTilalmiIdoszakban();
    }

    public boolean isVedett(){
        return getTipus().contains("Védett");
    }

    public boolean isInvaziv(){
        return getTipus().contains("Invazív");
    }

    public boolean isIdoszakosFelmentesselFoghato(){
        return getTipus().contains("időszakos felmentéssel fogható");
    }
}
