package com.paulohva.bustracker.model;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.RouteRequest;

import java.util.List;
import java.util.Map;

public class RerouteResponse {
    private Map<String, List<DublinkedData>> schedule;
    private Map<String, RouteRequest> routes;

    // Constructors, getters, setters
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public boolean isRerouted() {
        return rerouted;
    }

    public void setRerouted(boolean rerouted) {
        this.rerouted = rerouted;
    }

    public Map<String, List<DublinkedData>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, List<DublinkedData>> schedule) {
        this.schedule = schedule;
    }
    public RerouteResponse() {
    }
    public RerouteResponse(Map<String, List<DublinkedData>> schedule, boolean rerouted, String cause) {
        this.schedule = schedule;
        this.rerouted = rerouted;
        this.cause = cause;
    }

    private boolean rerouted;
    private String cause;

    public void setRoutes(Map<String, RouteRequest> routes) {
        this.routes = routes;
    }
}
