package com.paulohva.bustracker.api;

import com.paulohva.bustracker.dto.Vehicle;
import com.paulohva.bustracker.services.DublinkedDataService;
import com.paulohva.bustracker.util.ConverterUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleResource {

    private DublinkedDataService service;

    public VehicleResource(DublinkedDataService service) {
        this.service = service;
    }

    @ApiOperation(value = "Find vehicle by fleet in a time frame", nickname = "findByFleet")
    @RequestMapping(value = "/findByFleet", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @ApiParam(value = "Date that need to be considered for filter. Must be in the format yyyy/MM/dd", required = true)
            @RequestParam(value = "date", required = true) String date,
            @ApiParam(value = "Start time that need to be considered for filter. Must be in the format HH:MM", required = true)
            @RequestParam(value = "startTime", required = true) String startTime,
            @ApiParam(value = "End time that need to be considered for filter. Must be in the format HH:MM", required = true)
            @RequestParam(value = "endTime", required = true) String endTime,
            @ApiParam(value = "Fleet identification also know as 'operator'", required = true)
            @RequestParam(value = "fleet", required = true) String fleet,
            @ApiParam(value = "Optional flag to filter only vehicles at stop", required = false)
            @RequestParam(value = "onlyAtStop", required = false) boolean onlyAtStop) {
        LocalDateTime startDateTime = ConverterUtils.getLocalDateTimeFromString(date, startTime);
        LocalDateTime endDateTime = ConverterUtils.getLocalDateTimeFromString(date, endTime);
        List<Vehicle> obj = service.findVehiclesByOperator(ConverterUtils.getMicrosecondsFromLocalDateTime(startDateTime)
                , ConverterUtils.getMicrosecondsFromLocalDateTime(endDateTime), fleet, onlyAtStop);
        return ResponseEntity.ok().body(obj);
    }
}
