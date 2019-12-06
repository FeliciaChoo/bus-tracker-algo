package com.paulohva.bustracker.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class StandardDto implements Serializable {

    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;

    public StandardDto(ZonedDateTime fromDate, ZonedDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public StandardDto() {
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getToDate() {
        return toDate;
    }

    public void setToDate(ZonedDateTime toDate) {
        this.toDate = toDate;
    }
}
