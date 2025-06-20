package com.paulohva.bustracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "stops")
public class Stop {

    @Id
    private String id;

    private String name;

    @Field("lat")
    private double latitude;

    @Field("lng")
    private double longitude;

    private String time;

    private int demand;


    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    private String weather;

    public Stop() {}

    public Stop(String name, double latitude, double longitude, String time, int demand, String weather ) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.demand = demand;
        this.weather = weather;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getDemand() { return demand; }
    public void setDemand(int demand) { this.demand = demand; }
}
