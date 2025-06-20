package com.paulohva.bustracker.api;

import com.paulohva.bustracker.model.Stop;
import com.paulohva.bustracker.repository.StopRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stops")
public class StopController {

    private final StopRepository stopRepository;

    public StopController(StopRepository stopRepository) {
        System.out.println("✅ StopController initialized");
        this.stopRepository = stopRepository;
    }

    @GetMapping
    public List<Stop> getAllStops() {
        List<Stop> stops = stopRepository.findAll();
        System.out.println("📦 getAllStops() called - Fetched " + stops.size() + " stops");
        for (Stop s : stops) {
            System.out.printf("➡️  Stop: %s | Lat: %.4f | Lng: %.4f | Demand: %d%n",
                    s.getName(), s.getLatitude(), s.getLongitude(), s.getDemand());
        }
        return stops;
    }
}
