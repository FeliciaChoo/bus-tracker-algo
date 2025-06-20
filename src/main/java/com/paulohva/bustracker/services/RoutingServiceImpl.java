package com.paulohva.bustracker.services;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.Solutions;
import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.PathPoint;
import com.paulohva.bustracker.dto.RoutePlanResponse;
import com.paulohva.bustracker.dto.RouteRequest;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.model.Stop;
import com.paulohva.bustracker.repository.BusTraceRepository;
import com.paulohva.bustracker.repository.StopRepository;
import com.paulohva.bustracker.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class RoutingServiceImpl implements RoutingService {
    @Autowired
    private ORSService orsService;
    private final BusTraceRepository busTraceRepository;
    private final StopRepository stopRepository;

    @Autowired
    public RoutingServiceImpl(BusTraceRepository busTraceRepository,
                              StopRepository stopRepository) {
        this.busTraceRepository = busTraceRepository;
        this.stopRepository = stopRepository;
    }



    @Override
    public RouteRequest calculateOptimizedRoute(List<Stop> stops) {
        List<PathPoint> fullPath = new ArrayList<>();

        for (int i = 0; i < stops.size() - 1; i++) {
            Stop from = stops.get(i);
            Stop to = stops.get(i + 1);

            List<double[]> segment = orsService.getRoadPath(
                    from.getLatitude(), from.getLongitude(),
                    to.getLatitude(), to.getLongitude()
            );

            for (double[] coord : segment) {
                fullPath.add(new PathPoint(coord[0], coord[1]));
            }

            // 🛑 FIX: catch InterruptedException here
            try {
                Thread.sleep(1500); // Delay to avoid ORS rate limits (503)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Good practice
                System.err.println("❌ Sleep interrupted: " + e.getMessage());
            }
        }

        if (!stops.isEmpty()) {
            Stop last = stops.get(stops.size() - 1);
            fullPath.add(new PathPoint(last.getLatitude(), last.getLongitude()));
        }

        RouteRequest result = new RouteRequest();
        result.setStops(stops);
        result.setPath(fullPath);

        return result;
    }


    @Override
    public DublinkedData getLiveLocation(String vehicleID) {
        try {
            int id = Integer.parseInt(vehicleID);
            DublinkedData data = getLiveLocation(id);
            System.out.println("✅ Found live data for vehicleID " + id + ": " + data);
            return data;
        } catch (NumberFormatException e) {
            System.err.println("❌ Invalid vehicle ID format: " + vehicleID);
            throw new ResourceNotFoundException("Invalid vehicle ID: " + vehicleID);
        } catch (ResourceNotFoundException e) {
            System.err.println("❌ No data found for vehicleID " + vehicleID);
            throw e;
        }
    }

    @Override
    public DublinkedData getLiveLocation(int vehicleID) {
        return busTraceRepository.findFirstByVehicleIDOrderByTimestampDesc(vehicleID)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    }


        @Override
    public VehicleRoutingProblemSolution solveRoute(int capacity) {
        List<Stop> stops = stopRepository.findAll();
        return solveRoute(stops, capacity);
    }

    @Override
    public VehicleRoutingProblemSolution solveRoute(List<Stop> stops, int capacity) {
        double defaultStartLat = 3.0;
        double defaultStartLon = 101.7;
        double defaultEndLat = 3.0;
        double defaultEndLon = 101.7;
        return solveRoute(defaultStartLat, defaultStartLon, defaultEndLat, defaultEndLon, stops, capacity);
    }

    @Override
    public VehicleRoutingProblemSolution solveRoute(
            double startLat, double startLon,
            double endLat, double endLon,
            List<Stop> stops, int capacity) {

        VehicleTypeImpl vehicleType = VehicleTypeImpl.Builder.newInstance("bus-type")
                .addCapacityDimension(0, capacity)
                .build();

        VehicleImpl vehicle = VehicleImpl.Builder.newInstance("bus1")
                .setStartLocation(Location.newInstance(startLon, startLat))
                .setEndLocation(Location.newInstance(endLon, endLat))
                .setType(vehicleType)
                .build();

        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);

        for (Stop s : stops) {
            com.graphhopper.jsprit.core.problem.job.Service service =
                    com.graphhopper.jsprit.core.problem.job.Service.Builder
                            .newInstance("stop-" + s.getId())
                            .setLocation(Location.newInstance(s.getLongitude(), s.getLatitude()))
                            .addSizeDimension(0, s.getDemand())
                            .build();
            vrpBuilder.addJob(service);
        }

        VehicleRoutingProblem problem = vrpBuilder.build();
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();

        return Solutions.bestOf(solutions);
    }

    private double calculateDistance(DublinkedData location, Stop stop) {
        return Math.sqrt(
                Math.pow(location.getLatWGS84() - stop.getLatitude(), 2) +
                        Math.pow(location.getLonWGS84() - stop.getLongitude(), 2)
        );
    }

    private Stop findNearestStop(Double userLat, Double userLng, List<Stop> stops) {
        return stops.stream()
                .min(Comparator.comparingDouble(stop ->
                        Math.sqrt(
                                Math.pow(userLat - stop.getLatitude(), 2) +
                                        Math.pow(userLng - stop.getLongitude(), 2)
                        )
                ))
                .orElse(stops.get(0));
    }

    private List<Trace> calculatePath(Double startLat, Double startLng,
                                      Double endLat, Double endLng) {
        List<Trace> path = new ArrayList<>();

        Trace start = new Trace();
        start.setLatWGS84(startLat);
        start.setLonWGS84(startLng);
        path.add(start);

        Trace end = new Trace();
        end.setLatWGS84(endLat);
        end.setLonWGS84(endLng);
        path.add(end);

        return path;
    }
}