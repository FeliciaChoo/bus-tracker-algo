package com.paulohva.bustracker.api;


import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.model.Stop;
import com.paulohva.bustracker.services.SmartRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/schedule")
public class BusStopSchedulerController {

    @Autowired
    private SmartRoutingService smartRoutingService;

    @PostMapping("/from-stops")
    public Map<String, List<DublinkedData>> scheduleFromStops(
            @RequestBody List<Stop> stops,
            @RequestParam(defaultValue = "3") int buses
    ) {
        List<DublinkedData> traces = convertStopsToTraces(stops);
        return smartRoutingService.scheduleBusesByDemand(traces, buses);
    }

    private List<DublinkedData> convertStopsToTraces(List<Stop> stops) {
        List<DublinkedData> traces = new ArrayList<>();
        for (Stop stop : stops) {
            long timestamp = parseTimeToTimestamp(stop.getTime());

            DublinkedData trace = new DublinkedData();
            trace.setVehicleID(2001); // or assign dynamically
            trace.setLatWGS84(stop.getLatitude());
            trace.setLonWGS84(stop.getLongitude());
            trace.setTimestamp(timestamp);
            trace.setWeather("sunny"); // or pull from external API
            trace.setDayType("weekday");
            trace.setCheckInCount(stop.getDemand());
            trace.setCheckOutCount((int) Math.round(stop.getDemand() * 0.2));
            traces.add(trace);
        }
        return traces;
    }

    private long parseTimeToTimestamp(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Calendar cal = Calendar.getInstance();
            Date parsed = sdf.parse(time);
            cal.set(Calendar.HOUR_OF_DAY, parsed.getHours());
            cal.set(Calendar.MINUTE, parsed.getMinutes());
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTimeInMillis();
        } catch (Exception e) {
            return System.currentTimeMillis(); // fallback
        }
    }
}
