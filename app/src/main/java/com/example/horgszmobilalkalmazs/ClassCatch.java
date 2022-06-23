package com.example.horgszmobilalkalmazs;

public class ClassCatch {
    String dateOfCatch;
    String fishName;
    String image;
    int size;
    float weight;
    String bait;
    String location;

    public ClassCatch(String dateOfCatch, String fishName, String image, int size, float weight, String bait, String location) {
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

    public float getWeight() {
        return weight;
    }

    public String getBait() {
        return bait;
    }

    public String getLocation() {
        return location;
    }
}
