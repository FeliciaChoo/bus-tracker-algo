package com.paulohva.bustracker.api;

import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.services.DublinkedDataService;
import com.paulohva.bustracker.util.ConverterUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping(value = "/activity")
public class ActivityResource {
    private DublinkedDataService service;

    public ActivityResource(DublinkedDataService service) {
        this.service = service;
    }

    @ApiOperation(value = "Find all activity of a vehicle in a time frame", nickname = "findByVehicle")
    @RequestMapping(value = "/findByVehicle", method = RequestMethod.GET)
    public ResponseEntity<List<Trace>> searchTraceByVehicleID(
            @ApiParam(value = "Date that need to be considered for filter. Must be in the format yyyy/MM/dd.", required = true)
            @RequestParam(value = "date", required = true) String date,
            @ApiParam(value = "Start time that need to be considered for filter. Must be in the format HH:MM", format = "time", required = true)
            @RequestParam(value = "startTime", required = true) String startTime,
            @ApiParam(value = "End time that need to be considered for filter. Must be in the format HH:MM", format = "time", required = true)
            @RequestParam(value = "endTime", required = true) String endTime,
            @ApiParam(value = "Vehicle identification know as 'vehicleID'", format = "time", required = true)
            @RequestParam(value = "vehicleID", required = true) int vehicleID) {
        LocalDateTime startDateTime = ConverterUtils.getLocalDateTimeFromString(date, startTime);
        LocalDateTime endDateTime = ConverterUtils.getLocalDateTimeFromString(date, endTime);
        List<Trace> obj = service.findTraceByVehicle(ConverterUtils.getMicrosecondsFromLocalDateTime(startDateTime)
                , ConverterUtils.getMicrosecondsFromLocalDateTime(endDateTime), vehicleID);
        return ResponseEntity.ok().body(obj);
    }
}
