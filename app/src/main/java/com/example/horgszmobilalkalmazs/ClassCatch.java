package com.example.horgszmobilalkalmazs;

public class ClassCatch {
    private String dateOfCatch;
    private String fishName;
    private String image;
    private int size;
    private int weight;
    private String bait;
    private String location;

    public ClassCatch(String dateOfCatch, String fishName, String image, int size, int weight, String bait, String location) {
        this.dateOfCatch = dateOfCatch;
        this.fishName = fishName;
        this.image = image;
        this.size = size;
        this.weight = weight;
        this.bait = bait;
        this.location = location;
    }

    public String getDateOfCatch() {
        return dateOfCatch;
    }

    public String getFishName() {
        return fishName;
    }

    public String getImage() {
        return image;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public String getBait() {
        return bait;
    }

    public String getLocation() {
        return location;
    }
}
