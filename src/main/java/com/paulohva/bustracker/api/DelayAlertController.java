package com.paulohva.bustracker.api;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.RouteRequest;
import com.paulohva.bustracker.model.Stop;
import com.paulohva.bustracker.repository.DublinkedDataRepo;
import com.paulohva.bustracker.repository.StopRepository;
import com.paulohva.bustracker.services.SmartRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/alerts")
public class DelayAlertController {

    @Autowired
    private DublinkedDataRepo gpsRepo;

    @Autowired
    private StopRepository stopRepo;

    @Autowired
    private SmartRoutingService adjustmentService;

    @GetMapping("/delay/{vehicleID}")
    public ResponseEntity<?> checkDelay(@PathVariable int vehicleID) {
        Optional<DublinkedData> latest = gpsRepo.findTopByVehicleIDOrderByTimestampDesc(vehicleID);
        if (latest.isEmpty()) return ResponseEntity.notFound().build();

        List<Stop> stops = stopRepo.findAll();
        Stop nearest = findNearestStop(stops, latest.get());
        boolean delayed = adjustmentService.isBusDelayed(latest.get(), nearest);

        Map<String, Object> alert = new HashMap<>();
        alert.put("vehicleID", vehicleID);
        alert.put("delayed", delayed);
        alert.put("stop", nearest.getName());
        alert.put("scheduled", nearest.getTime());
        alert.put("actual", Instant.ofEpochMilli(latest.get().getTimestamp())
                .atZone(ZoneId.systemDefault()).toLocalTime().toString());

        return ResponseEntity.ok(alert);
    }

    private Stop findNearestStop(List<Stop> stops, DublinkedData data) {
        return stops.stream()
                .min(Comparator.comparingDouble(s ->
                        Math.sqrt(Math.pow(data.getLatWGS84() - s.getLatitude(), 2) +
                                Math.pow(data.getLonWGS84() - s.getLongitude(), 2))))
                .orElse(null);
    }
    public Map<String, RouteRequest> adjustRoutesWithFlag(
            Map<String, List<DublinkedData>> busData,
            Map<String, String> flags
    ) {
        Map<String, RouteRequest> adjustedRoutes = new HashMap<>();

        for (Map.Entry<String, List<DublinkedData>> entry : busData.entrySet()) {
            String busId = entry.getKey();
            List<DublinkedData> data = entry.getValue();
            String flag = flags.getOrDefault(busId, "normal");

            List<Stop> stops = data.stream().map(point -> {
                Stop stop = new Stop();
                stop.setLatitude(point.getLatWGS84());
                stop.setLongitude(point.getLonWGS84());
                stop.setDemand((int)(Math.random() * 5 + 1)); // Dummy demand
                return stop;
            }).collect(Collectors.toList());

            if (!"normal".equalsIgnoreCase(flag)) {
                // Apply rerouting logic, for example: prioritize high-demand or nearby stops
                stops.sort(Comparator.comparingInt(Stop::getDemand).reversed());
            }

            RouteRequest optimized = adjustmentService.calculateOptimizedRoute(stops); // ✅ Correct
            adjustedRoutes.put(busId, optimized);
        }

        return adjustedRoutes;
    }



}
