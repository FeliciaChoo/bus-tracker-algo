package com.paulohva.bustracker.dto;

import com.paulohva.bustracker.model.Stop;
import java.util.List;

public class RouteRequest {

    private List<Stop> stops;
    private List<PathPoint> path;  // ✅ ADD THIS LINE

    public RouteRequest() {
    }


    public RouteRequest(List<Stop> stops, List<PathPoint> path) {
        this.stops = stops;
        this.path = path;
    }

    public RouteRequest(List<Stop> stops) {
        this.stops = stops;
        this.path = new java.util.ArrayList<>(); // ✅ Prevents null issues
    }


    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<PathPoint> getPath() {
        if (path == null) {
            path = new java.util.ArrayList<>();
        }
        return path;
    }

    public void setPath(List<PathPoint> path) {
        this.path = path;
    }
}
