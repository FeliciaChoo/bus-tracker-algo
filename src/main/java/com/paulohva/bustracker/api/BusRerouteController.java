package com.paulohva.bustracker.api;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.model.RerouteResponse;
import com.paulohva.bustracker.services.SmartRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class BusRerouteController {

    @Autowired
    private SmartRoutingService routingService;

    // Simulated event map
    private static final Map<String, String> events = Map.of("Bus-2", "delay");

    @PostMapping("/reroute-if-needed")
    public ResponseEntity<RerouteResponse> checkAndReroute(@RequestBody Map<String, List<DublinkedData>> schedule) {
        RerouteResponse result = routingService.adjustRoutesWithFlag(schedule, events);
        return ResponseEntity.ok(result);
    }
}
