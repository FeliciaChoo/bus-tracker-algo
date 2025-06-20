package com.paulohva.bustracker.dto;

public class PathPoint {
    private double lat;
    private double lng;

    // Add this constructor
    public PathPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // Default constructor (if needed)
    public PathPoint() {}

    // Getters and setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
