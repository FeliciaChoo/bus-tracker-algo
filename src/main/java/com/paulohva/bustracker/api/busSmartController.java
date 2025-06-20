package com.paulohva.bustracker.api;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.RouteRequest;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.repository.BusTraceRepository;
import com.paulohva.bustracker.services.SmartRoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// BusSmartController.java
@RestController
@RequestMapping("/api/smart")
public class busSmartController {

    private final BusTraceRepository traceRepo;
    private final SmartRoutingService smartService;

    public busSmartController(BusTraceRepository traceRepo, SmartRoutingService smartService) {
        this.traceRepo = traceRepo;
        this.smartService = smartService;
    }

    @GetMapping("/route/{vehicleId}")
    public ResponseEntity<List<Trace>> getSmartRoute(@PathVariable int vehicleId) {
        List<DublinkedData> rawTraces = traceRepo.findByVehicleID(vehicleId);
        RouteRequest request = smartService.predictDemandAndOptimizeRoute(rawTraces);

// Convert PathPoint → Trace if necessary
        List<Trace> optimizedRoute = request.getPath().stream()
                .map(p -> new Trace(p.getLat(), p.getLng()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(optimizedRoute);
    }
}

