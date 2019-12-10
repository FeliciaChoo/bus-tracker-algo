package com.paulohva.bustracker.resources;

import com.paulohva.bustracker.dto.Fleet;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.dto.Vehicle;
import com.paulohva.bustracker.services.DublinkedDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value="/bustracker")
public class BusTrackerResource {

    private DublinkedDataService service;

    public BusTrackerResource(DublinkedDataService service) {
        this.service = service;
    }

    @RequestMapping(value="/searchOperators",method= RequestMethod.GET)
    public ResponseEntity<List<Fleet>> searchOperators(
            @RequestParam(value="date", defaultValue="") String date,
            @RequestParam(value="startTime", defaultValue="") String startTime,
            @RequestParam(value="endTime", defaultValue="") String endTime) {

        LocalDate typedDate = LocalDate.parse(date);
        LocalTime typedStartTime = LocalTime.parse(startTime);
        LocalTime typedEndTime = LocalTime.parse(endTime);

        LocalDateTime dateGreaterThan = LocalDateTime.of(typedDate,typedStartTime);
        LocalDateTime dateLesserThan = LocalDateTime.of(typedDate,typedEndTime);

        //falta converter e mandar time zone pela request
        long fromDate = TimeUnit.SECONDS.toMicros(dateGreaterThan.toEpochSecond(ZoneOffset.UTC));
        long toDate = TimeUnit.SECONDS.toMicros(dateLesserThan.toEpochSecond(ZoneOffset.UTC));
        List<Fleet> obj = service.findOperators(fromDate, toDate);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/searchVehiclesByOperator",method= RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @RequestParam(value="date", defaultValue="") String date,
            @RequestParam(value="startTime", defaultValue="") String startTime,
            @RequestParam(value="endTime", defaultValue="") String endTime,
            @RequestParam(value="operator", defaultValue="") String operator) {

        LocalDate typedDate = LocalDate.parse(date);
        LocalTime typedStartTime = LocalTime.parse(startTime);
        LocalTime typedEndTime = LocalTime.parse(endTime);

        LocalDateTime dateGreaterThan = LocalDateTime.of(typedDate,typedStartTime);
        LocalDateTime dateLesserThan = LocalDateTime.of(typedDate,typedEndTime);

        //falta converter e mandar time zone pela request
        long fromDate = TimeUnit.SECONDS.toMicros(dateGreaterThan.toEpochSecond(ZoneOffset.UTC));
        long toDate = TimeUnit.SECONDS.toMicros(dateLesserThan.toEpochSecond(ZoneOffset.UTC));
        List<Vehicle> obj = service.findVehiclesByOperator(fromDate, toDate,operator, false);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/searchVehiclesAtStopByOperator",method= RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> searchVehiclesAtStop(
            @RequestParam(value="date", defaultValue="") String date,
            @RequestParam(value="startTime", defaultValue="") String startTime,
            @RequestParam(value="endTime", defaultValue="") String endTime,
            @RequestParam(value="operator", defaultValue="") String operator) {

        LocalDate typedDate = LocalDate.parse(date);
        LocalTime typedStartTime = LocalTime.parse(startTime);
        LocalTime typedEndTime = LocalTime.parse(endTime);

        LocalDateTime dateGreaterThan = LocalDateTime.of(typedDate,typedStartTime);
        LocalDateTime dateLesserThan = LocalDateTime.of(typedDate,typedEndTime);

        //falta converter e mandar time zone pela request
        long fromDate = TimeUnit.SECONDS.toMicros(dateGreaterThan.toEpochSecond(ZoneOffset.UTC));
        long toDate = TimeUnit.SECONDS.toMicros(dateLesserThan.toEpochSecond(ZoneOffset.UTC));
        List<Vehicle> obj = service.findVehiclesByOperator(fromDate, toDate,operator, true);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/searchTraceByVehicleID",method= RequestMethod.GET)
    public ResponseEntity<List<Trace>> searchTraceByVehicleID(
            @RequestParam(value="date", defaultValue="") String date,
            @RequestParam(value="startTime", defaultValue="") String startTime,
            @RequestParam(value="endTime", defaultValue="") String endTime,
            @RequestParam(value="vehicleID", defaultValue="") int vehicleID) {

        LocalDate typedDate = LocalDate.parse(date);
        LocalTime typedStartTime = LocalTime.parse(startTime);
        LocalTime typedEndTime = LocalTime.parse(endTime);

        LocalDateTime dateGreaterThan = LocalDateTime.of(typedDate,typedStartTime);
        LocalDateTime dateLesserThan = LocalDateTime.of(typedDate,typedEndTime);

        //falta converter e mandar time zone pela request
        long fromDate = TimeUnit.SECONDS.toMicros(dateGreaterThan.toEpochSecond(ZoneOffset.UTC));
        long toDate = TimeUnit.SECONDS.toMicros(dateLesserThan.toEpochSecond(ZoneOffset.UTC));
        List<Trace> obj = service.findTraceByVehicle(fromDate, toDate,vehicleID);
        return ResponseEntity.ok().body(obj);
    }

}
