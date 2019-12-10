package com.paulohva.bustracker.api;

import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.services.DublinkedDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value="/activity")
public class ActivityResource {
    private DublinkedDataService service;

    public ActivityResource(DublinkedDataService service) {
        this.service = service;
    }

    @ApiOperation(value = "Find all activity of a vehicle in a time frame", nickname = "findByVehicle")
    @RequestMapping(value="/findByVehicle",method= RequestMethod.GET)
    public ResponseEntity<List<Trace>> searchTraceByVehicleID(
            @RequestParam(value="date", required = true) String date,
            @RequestParam(value="startTime", required = true) String startTime,
            @RequestParam(value="endTime", required = true) String endTime,
            @RequestParam(value="vehicleID", required = true) int vehicleID) {

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
