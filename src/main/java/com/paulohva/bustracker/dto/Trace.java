package com.paulohva.bustracker.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

public class Trace implements Serializable {
    private long timestamp;
    private double lonWGS84;
    private double latWGS84;

    public Trace() {
    }

    public Trace(long timestamp, double lonWGS84, double latWGS84) {
        this.timestamp = timestamp;
        this.lonWGS84 = lonWGS84;
        this.latWGS84 = latWGS84;
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
}
