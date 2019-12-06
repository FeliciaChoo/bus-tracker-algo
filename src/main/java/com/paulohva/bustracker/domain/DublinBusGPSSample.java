package com.paulohva.bustracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "dublinBusGPSSample")
public class DublinBusGPSSample implements Serializable {

    @Id
    private String id;
    private LocalDate timeFrame;
    private String operator;
    private long timestamp;

    public DublinBusGPSSample() {
    }

    public DublinBusGPSSample(String id, LocalDate timeFrame, String operator, long timestamp) {
        this.id = id;
        this.timeFrame = timeFrame;
        this.operator = operator;
        this.timestamp = timestamp;
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

    public LocalDate getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(LocalDate timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DublinBusGPSSample dublinBusGPSSample = (DublinBusGPSSample) o;
        return Objects.equals(id, dublinBusGPSSample.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
