package com.paulohva.bustracker.services;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.PathPoint;
import com.paulohva.bustracker.dto.RouteRequest;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.model.RerouteResponse;
import com.paulohva.bustracker.model.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SmartRoutingService {
    @Autowired
    private ORSService orsService;
    private Map<String, Double> weatherFactors = Map.of(
            "sunny", 1.0,
            "cloudy", 0.8,
            "rainy", 0.5
    );

    private double getWeatherFactor(String weather) {
        return weatherFactors.getOrDefault(weather.toLowerCase(), 1.0);
    }

    public List<PathPoint> getRoadBasedPath(double startLat, double startLng, double endLat, double endLng) {
        List<double[]> coords = orsService.getRoadPath(startLat, startLng, endLat, endLng);
        return coords.stream().map(c -> new PathPoint(c[0], c[1])).collect(Collectors.toList());
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public RouteRequest calculateOptimizedRoute(List<Stop> stops) {
        System.out.println("✅ Starting optimization with " + stops.size() + " stops");

        List<Stop> optimizedStops = findBestRoute(stops);

        System.out.println("🔍 Optimized Stops Count: " + optimizedStops.size());

        List<PathPoint> path = new ArrayList<>();

        if (optimizedStops.size() < 2) {
            System.err.println("❗ Not enough stops to calculate path.");
        }

        for (int i = 0; i < optimizedStops.size() - 1; i++) {
            Stop current = optimizedStops.get(i);
            Stop next = optimizedStops.get(i + 1);

            System.out.printf("🔗 Connecting stop %s -> %s via road\n", current.getName(), next.getName());

            List<PathPoint> segment = getRoadBasedPath(
                    current.getLatitude(), current.getLongitude(),
                    next.getLatitude(), next.getLongitude()
            );

            if (segment == null || segment.isEmpty()) {
                System.err.printf("❌ ORS returned empty segment for %s -> %s\n", current.getName(), next.getName());
            } else {
                System.out.println("📍 ORS returned segment with points: " + segment.size());
            }
            path.addAll(segment);
        }

        System.out.println("✅ Final Path Points Count: " + path.size());

        return new RouteRequest(
                optimizedStops != null ? optimizedStops : new ArrayList<>(),
                path != null ? path : new ArrayList<>()
        );
    }


    public List<Stop> findBestRoute(List<Stop> stops) {
        if (stops == null || stops.isEmpty()) return new ArrayList<>();

        List<Stop> optimized = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        Stop current = stops.get(0);
        optimized.add(current);
        visited.add(stops.indexOf(current));

        System.out.println("Starting from: " + current.getName());

        while (optimized.size() < stops.size()) {
            Stop bestNext = null;
            double bestScore = Double.MIN_VALUE;

            for (int i = 0; i < stops.size(); i++) {
                if (visited.contains(i)) continue;

                Stop candidate = stops.get(i);
                double distance = calculateDistance(
                        current.getLatitude(), current.getLongitude(),
                        candidate.getLatitude(), candidate.getLongitude());

                int demand = candidate.getDemand();
                String weather = candidate.getWeather();
                if (weather == null || weather.isEmpty()) {
                    weather = "sunny";
                    candidate.setWeather(weather); // Optional: store fallback in the object
                }
                double weatherFactor = getWeatherFactor(weather);
                double score = (demand * weatherFactor) / (distance + 0.01);

                System.out.printf(
                        "Evaluating stop %s (Demand: %d, Weather: %s, Dist: %.2f) => Score: %.2f\n",
                        candidate.getName(), demand, weather, distance, score
                );

                if (score > bestScore) {
                    bestScore = score;
                    bestNext = candidate;
                }
            }

            if (bestNext != null) {
                optimized.add(bestNext);
                visited.add(stops.indexOf(bestNext));
                current = bestNext;
                System.out.println("Selected next stop: " + bestNext.getName());
            } else {
                System.err.println("❌ No valid next stop found. Ending optimization early.");
                break;
            }

        }

        System.out.println("Optimized stop order:");
        for (int i = 0; i < optimized.size(); i++) {
            System.out.printf("%d. %s (Demand: %d, Weather: %s)\n",
                    i + 1, optimized.get(i).getName(), optimized.get(i).getDemand(), optimized.get(i).getWeather());
        }

        return optimized;
    }

    public RouteRequest predictDemandAndOptimizeRoute(List<DublinkedData> liveData) {
        // Convert live data into stops with predicted demand
        List<Stop> predictedStops = liveData.stream().map(data -> {
            Stop stop = new Stop();
            stop.setLatitude(data.getLatWGS84());
            stop.setLongitude(data.getLonWGS84());

            // Dummy logic for demand prediction
            int predictedDemand = (int)(Math.random() * 10) + 1; // value between 1-10
            stop.setDemand(predictedDemand);

            return stop;
        }).collect(Collectors.toList());

        return calculateOptimizedRoute(predictedStops);
    }
    public Map<String, List<DublinkedData>> scheduleBusesByDemand(List<DublinkedData> liveData, int busCount) {
        // Step 1: Predict demand from liveData
        List<Stop> allStops = liveData.stream().map(data -> {
            Stop stop = new Stop();
            stop.setLatitude(data.getLatWGS84());
            stop.setLongitude(data.getLonWGS84());
            stop.setDemand((int)(Math.random() * 10) + 1); // dummy demand prediction
            return stop;
        }).collect(Collectors.toList());

        // Step 2: Sort by demand and split into groups for each bus
        allStops.sort(Comparator.comparingInt(Stop::getDemand).reversed());

        List<List<Stop>> busAssignments = new ArrayList<>();
        for (int i = 0; i < busCount; i++) {
            busAssignments.add(new ArrayList<>());
        }

        for (int i = 0; i < allStops.size(); i++) {
            busAssignments.get(i % busCount).add(allStops.get(i));
        }

        // Step 3: Convert each route to List<DublinkedData> and map to Bus ID
        Map<String, List<DublinkedData>> busMap = new HashMap<>();

        for (int i = 0; i < busAssignments.size(); i++) {
            String busId = "Bus-" + (i + 1);
            List<Stop> stops = busAssignments.get(i);
            RouteRequest route = calculateOptimizedRoute(stops);

            List<DublinkedData> tracePoints = convertPathPointsToDublinked(route.getPath());
            busMap.put(busId, tracePoints);
        }

        return busMap;
    }

    private List<DublinkedData> convertPathPointsToDublinked(List<PathPoint> pathPoints) {
        List<DublinkedData> list = new ArrayList<>();
        for (PathPoint p : pathPoints) {
            DublinkedData data = new DublinkedData();
            data.setLatWGS84(p.getLat());
            data.setLonWGS84(p.getLng());
            data.setTimestamp(System.currentTimeMillis()); // or leave it unset if not needed
            list.add(data);
        }
        return list;
    }


    public boolean isBusDelayed(DublinkedData currentLocation, Stop nextStop) {
        if (currentLocation == null || nextStop == null) return false;

        double distance = calculateDistance(
                currentLocation.getLatWGS84(), currentLocation.getLonWGS84(),
                nextStop.getLatitude(), nextStop.getLongitude()
        );

        // Assume bus is delayed if it's still >500 meters away and hasn't moved recently
        long currentTime = System.currentTimeMillis();
        long lastUpdate = currentLocation.getTimestamp();

        boolean slowUpdate = (currentTime - lastUpdate) > 2 * 60 * 1000; // 2 minutes
        boolean farAway = distance > 0.5; // in kilometers

        return slowUpdate && farAway;
    }


    public RerouteResponse adjustRoutesWithFlag(
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
                stops.sort(Comparator.comparingInt(Stop::getDemand).reversed());
            }

            RouteRequest optimized = calculateOptimizedRoute(stops);
            adjustedRoutes.put(busId, optimized);
        }

        // ✅ Wrap it in RerouteResponse
        RerouteResponse response = new RerouteResponse();
        response.setRoutes(adjustedRoutes);
        return response;
    }


}
