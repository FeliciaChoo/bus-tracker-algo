package com.paulohva.bustracker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "dublinBusGPSSample")
public class DublinkedData implements Serializable {

    @Id
    private String id;

    @Field("timeFrame")
    private String timeFrame;

    @Field("operator")
    private String operator;

    @Field("timestamp")
    private long timestamp;

    @Field("vehicleID")
    private int vehicleID;

    @Field("atStop")
    private int atStop;

    @Field("lat")  // ✅ Mongo field is "lat"
    private double lat;

    @Field("lng")  // ✅ Mongo field is "lng"
    private double lon;

    @Field("weather")
    private String weather;

    @Field("dayType")
    private String dayType;

    @Field("checkInCount")
    private int checkInCount;

    @Field("checkOutCount")
    private int checkOutCount;

    // 👇 Map frontend "demand" JSON key to checkInCount
    @JsonProperty("demand")
    public void setDemand(int demand) {
        this.checkInCount = demand;
    }

    public DublinkedData() {}

    // Standard getters and setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTimeFrame() { return timeFrame; }
    public void setTimeFrame(String timeFrame) { this.timeFrame = timeFrame; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getVehicleID() { return vehicleID; }
    public void setVehicleID(int vehicleID) { this.vehicleID = vehicleID; }

    public int getAtStop() { return atStop; }
    public void setAtStop(int atStop) { this.atStop = atStop; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }

    // ✅ Used by backend routing logic
    public double getLatWGS84() { return lat; }
    public void setLatWGS84(double lat) { this.lat = lat; }

    public double getLonWGS84() { return lon; }
    public void setLonWGS84(double lon) { this.lon = lon; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public String getDayType() { return dayType; }
    public void setDayType(String dayType) { this.dayType = dayType; }

    public int getCheckInCount() { return checkInCount; }
    public void setCheckInCount(int checkInCount) { this.checkInCount = checkInCount; }

    public int getCheckOutCount() { return checkOutCount; }
    public void setCheckOutCount(int checkOutCount) { this.checkOutCount = checkOutCount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DublinkedData)) return false;
        DublinkedData that = (DublinkedData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
