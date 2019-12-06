package com.paulohva.bustracker.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class StandardDto implements Serializable {

    private LocalDate timeFrame;
    private LocalTime startTime;
    private LocalTime finishTime;

    public StandardDto() {
    }

    public StandardDto(LocalDate timeFrame, LocalTime startTime, LocalTime finishTime) {
        this.timeFrame = timeFrame;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public LocalDate getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(LocalDate timeFrame) {
        this.timeFrame = timeFrame;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }
}
