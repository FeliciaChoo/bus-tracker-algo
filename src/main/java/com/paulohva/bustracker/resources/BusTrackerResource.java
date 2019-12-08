package com.paulohva.bustracker.resources;

import com.paulohva.bustracker.domain.DublinBusGPSSample;
import com.paulohva.bustracker.dto.Operator;
import com.paulohva.bustracker.dto.StandardDto;
import com.paulohva.bustracker.dto.VehicleID;
import com.paulohva.bustracker.services.DublinBusGPSSampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value="/bustracker")
public class BusTrackerResource {

    private DublinBusGPSSampleService service;

    public BusTrackerResource(DublinBusGPSSampleService service) {
        this.service = service;
    }

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public ResponseEntity<DublinBusGPSSample> findById(@PathVariable String id) {
        DublinBusGPSSample obj = service.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<List<DublinBusGPSSample>> findByDateBetween(@RequestBody StandardDto objDto) {

        LocalDateTime dateGreaterThan = LocalDateTime.of(objDto.getTimeFrame(),objDto.getStartTime());
        LocalDateTime dateLesserThan = LocalDateTime.of(objDto.getTimeFrame(),objDto.getFinishTime());

        //falta converter e mandar time zone pela request
        long fromDate = TimeUnit.SECONDS.toMicros(dateGreaterThan.toEpochSecond(ZoneOffset.UTC));
        long toDate = TimeUnit.SECONDS.toMicros(dateLesserThan.toEpochSecond(ZoneOffset.UTC));
        List<DublinBusGPSSample> objReturn = service.findByTimestampBetween(fromDate, toDate);
        return ResponseEntity.ok().body(objReturn);
    }

    @RequestMapping(value="/searchOperators",method=RequestMethod.GET)
    public ResponseEntity<List<Operator>> searchOperators(
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
        List<Operator> obj = service.findAllOperatorsByTimeFrame(fromDate, toDate);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/searchVehiclesByOperator",method=RequestMethod.GET)
    public ResponseEntity<List<VehicleID>> searchVehicles(
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
        List<VehicleID> obj = service.findVehiclesByTimeFrameAndOperator(fromDate, toDate,operator);
        return ResponseEntity.ok().body(obj);
    }

}
