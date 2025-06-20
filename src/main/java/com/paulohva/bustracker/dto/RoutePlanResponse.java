package com.paulohva.bustracker.dto;

import com.paulohva.bustracker.domain.DublinkedData;

import java.util.List;

public class RoutePlanResponse {
    private double startLat;
    private double startLon;
    private double endLat;
    private double endLon;

    private List<DublinkedData> path;
    private int stops;
    private int passengers;
    private List<Trace> userToStopPath;

    // Getters and setters
    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getEndLon() {
        return endLon;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }

    public List<DublinkedData> getPath() {
        return path;
    }

    public void setPath(List<DublinkedData> path) {
        this.path = path;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public List<Trace> getUserToStopPath() {
        return userToStopPath;
    }

    public void setUserToStopPath(List<Trace> userToStopPath) {
        this.userToStopPath = userToStopPath;
    }
}
