package com.paulohva.bustracker.services;

import com.paulohva.bustracker.dto.Fleet;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.dto.Vehicle;

import java.util.List;

public interface DublinkedDataService {
    List<Vehicle> findVehiclesByOperator(long fromDate, long toDate, String operator, boolean onlyAtStop);

    List<Fleet> findOperators(long fromDate, long toDate);

    List<Trace> findTraceByVehicle(long fromDate, long toDate, int vehicleID);
}
