package com.paulohva.bustracker.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "trace")  // Tells Spring this maps to MongoDB "trace" collection
public class Trace implements Serializable {

    @Id
    private String id;  // Optional but good practice for MongoDB

    private int vehicleID;
    private long timestamp;
    private double lonWGS84;
    private double latWGS84;
    private List<Trace> allTraces;

    public Trace() {
    }
    public Trace(double lat, double lng) {
        this.latWGS84 = lat;
        this.lonWGS84 = lng;
    }

    public Trace(int vehicleID, long timestamp, double lonWGS84, double latWGS84) {
        this.vehicleID = vehicleID;
        this.timestamp = timestamp;
        this.lonWGS84 = lonWGS84;
        this.latWGS84 = latWGS84;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLonWGS84() {
        return lonWGS84;
    }

    public void setLonWGS84(double lonWGS84) {
        this.lonWGS84 = lonWGS84;
    }

    public double getLatWGS84() {
        return latWGS84;
    }

    public void setLatWGS84(double latWGS84) {
        this.latWGS84 = latWGS84;
    }

    public List<Trace> getAllTraces() {
        return allTraces;
    }

    public void setAllTraces(List<Trace> allTraces) {
        this.allTraces = allTraces;
    }

    public double getLat() {
        return this.latWGS84;
    }

    public double getLon() {
        return this.lonWGS84;
    }

    public Double getLng() {
        return this.lonWGS84;
    }

}

