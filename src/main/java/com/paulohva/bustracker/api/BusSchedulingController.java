package com.paulohva.bustracker.api;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.RouteRequest;
import com.paulohva.bustracker.model.RerouteResponse;
import com.paulohva.bustracker.services.SmartRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/schedule")
public class BusSchedulingController {

    @Autowired
    private SmartRoutingService smartRoutingService;

    @PostMapping("/by-demand")
    public Map<String, List<DublinkedData>> scheduleByDemand(
            @RequestBody List<DublinkedData> traces,
            @RequestParam(defaultValue = "3") int buses) {
        return smartRoutingService.scheduleBusesByDemand(traces, buses);
    }
    @PostMapping("/adjust")
    public RerouteResponse adjustInRealTime(
            @RequestBody Map<String, List<DublinkedData>> currentSchedule,
            @RequestParam Map<String, String> events
    ) {
        return smartRoutingService.adjustRoutesWithFlag(currentSchedule, events);
    }


}
