package com.paulohva.bustracker.dto;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Vehicle implements Serializable {
    @Id
    private String vehicleID;

    public Vehicle(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Vehicle() {
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
}
