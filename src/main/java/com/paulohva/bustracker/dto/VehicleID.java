package com.paulohva.bustracker.dto;

import org.springframework.data.annotation.Id;

public class VehicleID {
    @Id
    private String vehicleID;

    public VehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public VehicleID() {
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
}
