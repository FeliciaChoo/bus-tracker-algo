package com.paulohva.bustracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "dublinBusGPSSample")
public class DublinkedData implements Serializable {

    @Id
    private String id;
    private String timeFrame;
    private String operator;
    private long timestamp;
    private String vehicleID;
    private int atStop;

    public DublinkedData() {
    }

    public DublinkedData(String id, String timeFrame, String operator, long timestamp, String vehicleID, int atStop) {
        this.id = id;
        this.timeFrame = timeFrame;
        this.operator = operator;
        this.timestamp = timestamp;
        this.vehicleID = vehicleID;
        this.atStop = atStop;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int isAtStop() {
        return atStop;
    }

    public void setAtStop(int atStop) {
        this.atStop = atStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DublinkedData dublinkedData = (DublinkedData) o;
        return Objects.equals(id, dublinkedData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
