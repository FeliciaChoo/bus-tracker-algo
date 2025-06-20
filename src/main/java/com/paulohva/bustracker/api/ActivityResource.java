package com.paulohva.bustracker.api;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.services.DublinkedDataService;
import com.paulohva.bustracker.util.ConverterUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/activity")
public class ActivityResource {

    private final DublinkedDataService service;

    public ActivityResource(DublinkedDataService service) {
        this.service = service;
    }

    @ApiOperation(value = "Find all activity of a vehicle in a time frame", nickname = "findByVehicle")
    @RequestMapping(value = "/findByVehicle", method = RequestMethod.GET)
    public ResponseEntity<List<DublinkedData>> searchTraceByVehicleID(
            @ApiParam(value = "Date that need to be considered for filter. Must be in the format yyyy/MM/dd.", required = true)
            @RequestParam("date") String date,
            @ApiParam(value = "Start time that need to be considered for filter. Must be in the format HH:MM", required = true)
            @RequestParam("startTime") String startTime,
            @ApiParam(value = "End time that need to be considered for filter. Must be in the format HH:MM", required = true)
            @RequestParam("endTime") String endTime,
            @ApiParam(value = "Vehicle identification known as 'vehicleID'", required = true)
            @RequestParam("vehicleID") int vehicleID) {

        // Convert times
        LocalDateTime startDateTime = ConverterUtils.getLocalDateTimeFromString(date, startTime);
        LocalDateTime endDateTime = ConverterUtils.getLocalDateTimeFromString(date, endTime);

        long startMicros = ConverterUtils.getMicrosecondsFromLocalDateTime(startDateTime);
        long endMicros = ConverterUtils.getMicrosecondsFromLocalDateTime(endDateTime);

        // 🔍 Debug: Print the timestamp range
        System.out.println("🔍 Fetching vehicleID: " + vehicleID);
        System.out.println("🔍 Start: " + startDateTime + " → " + startMicros);
        System.out.println("🔍 End:   " + endDateTime + " → " + endMicros);

        // ✅ TEMP: Ignore time filtering for testing
        // List<Trace> obj = service.findTraceByVehicle(startMicros, endMicros, vehicleID);
        List<DublinkedData> obj = service.findTraceByVehicle(vehicleID); // ← try this version first!

        return ResponseEntity.ok().body(obj);
    }
}
