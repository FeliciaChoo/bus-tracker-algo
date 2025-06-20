package com.paulohva.bustracker.services;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.RoutePlanResponse;
import com.paulohva.bustracker.model.Stop;
import com.paulohva.bustracker.dto.RouteRequest;
import java.util.List;

public interface RoutingService {
    DublinkedData getLiveLocation(String vehicleID);

    DublinkedData getLiveLocation(int vehicleID);

    VehicleRoutingProblemSolution solveRoute(int capacity);

    VehicleRoutingProblemSolution solveRoute(List<Stop> stops, int capacity);

    VehicleRoutingProblemSolution solveRoute(double startLat, double startLon,
                                             double endLat, double endLon,
                                             List<Stop> stops, int capacity);

    RouteRequest calculateOptimizedRoute(List<Stop> stops); // ✅ keep only this

}
