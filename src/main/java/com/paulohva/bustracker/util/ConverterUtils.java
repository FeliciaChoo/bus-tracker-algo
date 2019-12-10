package com.paulohva.bustracker.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ConverterUtils {

    //static method to obtain microseconds from a localdatetime setting a fixed timezone (UTC)
    public static long getMicrosecondsFromLocalDateTime(LocalDateTime localDateTime) {
        //TODO: obtain timezone from requests
        return TimeUnit.SECONDS.toMicros(localDateTime.toEpochSecond(ZoneOffset.UTC));
    }

    //static method to obtain a localdatetime from a string date and a string time
    public static LocalDateTime getLocalDateTimeFromString(String date, String time) {
        //TODO: implement a proper Spring Data MongoDB converter
        LocalDate typedDate = LocalDate.parse(date);
        LocalTime typedTime = LocalTime.parse(time);
        return LocalDateTime.of(typedDate, typedTime);
    }
}
