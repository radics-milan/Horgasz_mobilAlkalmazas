package com.example.horgszmobilalkalmazs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClassFish {
    private String name;
    private String latinName;
    private int imageResourceId;
    private String type;
    private String closeSeason;
    private int minimumCatchSize;
    private int minimumCatchSizeInCloseSeason;
    private String description;

    public ClassFish(String name, String latinName, int imageResourceId, String type, String closeSeason, int minimumCatchSize, int minimumCatchSizeInCloseSeason, String description) {
        this.name = name;
        this.latinName = latinName;
        this.imageResourceId = imageResourceId;
        this.type = type;
        this.closeSeason = closeSeason;
        this.minimumCatchSize = minimumCatchSize;
        this.minimumCatchSizeInCloseSeason = minimumCatchSizeInCloseSeason;
        this.description = description;
    }

    public ClassFish(){
        //Firestore needs empty constructor
    }

    public String getName() {
        return name;
    }

    public String getLatinName() {
        return latinName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getType() {
        return type;
    }

    public String getCloseSeason() {
        return closeSeason;
    }

    public int getMinimumCatchSize() {
        return minimumCatchSize;
    }

    public int getMinimumCatchSizeInCloseSeason() {
        return minimumCatchSizeInCloseSeason;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCloseSeasonToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.", Locale.getDefault());
        String today = sdf.format(new Date());

        String[] currentDateParts = today.split("\\.");
        int currentMonth = Integer.parseInt(currentDateParts[0]);
        int currentDay = Integer.parseInt(currentDateParts[1]);

        if (getCloseSeason() != null) {
            String[] closeSeasonDateParts = getCloseSeason().split("-");
            String startDate = closeSeasonDateParts[0];
            String endDate = closeSeasonDateParts[1];

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

    public boolean isBiggerThanMinimumCatchSize(float size){
        return size >= getMinimumCatchSize();
    }

    public boolean isBiggerThanMinimumCatchSizeInCloseSeason(float size){
        return size >= getMinimumCatchSizeInCloseSeason();
    }
}
