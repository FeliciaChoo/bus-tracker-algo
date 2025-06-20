package com.paulohva.bustracker.api;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.RouteRequest;
import com.paulohva.bustracker.services.ORSService;
import com.paulohva.bustracker.services.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class RoutingController {

    private final RoutingService routingService;

    @Autowired
    private ORSService orsService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/test-route")
    public ResponseEntity<List<double[]>> testRoute() throws InterruptedException {
        double startLat = 2.9915;
        double startLng = 101.7084;
        double endLat = 3.0024;
        double endLng = 101.705879;

        List<double[]> path = orsService.getRoadPath(startLat, startLng, endLat, endLng);
        return ResponseEntity.ok(path);
    }

    @PostMapping("/optimized")
    public ResponseEntity<Map<String, Object>> getOptimizedRoute(@RequestBody RouteRequest request) {
        System.out.println("✅ [BACKEND] POST /api/routes/optimized called");
        System.out.println("Received stops: " + request.getStops());

        RouteRequest optimized = routingService.calculateOptimizedRoute(request.getStops());

        System.out.println("🔍 [DEBUG] Optimized Stops Count: " + optimized.getStops().size());
        System.out.println("🔍 [DEBUG] Path Points Count: " + optimized.getPath().size());

        for (int i = 0; i < optimized.getPath().size(); i++) {
            System.out.printf("   ↳ Point %d: (%.6f, %.6f)%n", i + 1,
                    optimized.getPath().get(i).getLat(),
                    optimized.getPath().get(i).getLng());
        }

        Map<String, Object> route = new HashMap<>();
        route.put("path", optimized.getPath());
        if (optimized.getPath() != null && !optimized.getPath().isEmpty()) {
            route.put("start", optimized.getPath().get(0));
        }
        route.put("distance", optimized.getPath().size());  // Optional: fake distance

        Map<String, Object> response = new HashMap<>();
        response.put("routes", List.of(route));

        return ResponseEntity.ok(response);
    }


    @GetMapping("/live/{vehicleID}")
    public ResponseEntity<Map<String, Object>> getLiveLocation(@PathVariable String vehicleID) {
        DublinkedData liveLocation = routingService.getLiveLocation(vehicleID);

        if (liveLocation == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new java.util.HashMap<>();
        response.put("latWGS84", liveLocation.getLat());
        response.put("lonWGS84", liveLocation.getLon());
        response.put("timestamp", liveLocation.getTimestamp());

        return ResponseEntity.ok(response);
    }
}
