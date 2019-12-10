package com.paulohva.bustracker.api;

import com.paulohva.bustracker.dto.Vehicle;
import com.paulohva.bustracker.services.DublinkedDataService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value="/vehicle")
public class VehicleResource {

    private DublinkedDataService service;

    public VehicleResource(DublinkedDataService service) {
        this.service = service;
    }

    @ApiOperation(value = "Find vehicle by fleet in a time frame", nickname = "findByFleet")
    @RequestMapping(value="/findByFleet",method= RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @RequestParam(value="date", required = true) String date,
             @RequestParam(value="startTime", required = true) String startTime,
             @RequestParam(value="endTime", required = true) String endTime,
             @RequestParam(value="fleet", required = true) String fleet,
            @RequestParam(value="onlyAtStop", required = false) boolean onlyAtStop) {

        LocalDate typedDate = LocalDate.parse(date);
        LocalTime typedStartTime = LocalTime.parse(startTime);
        LocalTime typedEndTime = LocalTime.parse(endTime);

        LocalDateTime dateGreaterThan = LocalDateTime.of(typedDate,typedStartTime);
        LocalDateTime dateLesserThan = LocalDateTime.of(typedDate,typedEndTime);

        //falta converter e mandar time zone pela request
        long fromDate = TimeUnit.SECONDS.toMicros(dateGreaterThan.toEpochSecond(ZoneOffset.UTC));
        long toDate = TimeUnit.SECONDS.toMicros(dateLesserThan.toEpochSecond(ZoneOffset.UTC));
        List<Vehicle> obj = service.findVehiclesByOperator(fromDate, toDate, fleet, onlyAtStop);
        return ResponseEntity.ok().body(obj);
    }
}
