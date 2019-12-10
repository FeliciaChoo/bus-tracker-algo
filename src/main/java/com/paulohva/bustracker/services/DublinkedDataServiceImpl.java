package com.paulohva.bustracker.services;

import com.paulohva.bustracker.dto.Fleet;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.dto.Vehicle;
import com.paulohva.bustracker.repository.DublinkedDataRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DublinkedDataServiceImpl implements DublinkedDataService {

    private DublinkedDataRepo repo;

    public DublinkedDataServiceImpl(DublinkedDataRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Vehicle> findVehiclesByOperator(long fromDate, long toDate, String operator, boolean onlyAtStop) {
        return repo.findVehiclesByOperator(fromDate, toDate, operator, onlyAtStop);
    }

    @Override
    public List<Fleet> findOperators(long fromDate, long toDate) {
        return repo.findOperators(fromDate,toDate);
    }

    @Override
    public List<Trace> findTraceByVehicle(long fromDate, long toDate, int vehicleID) {
        return repo.findTraceByVehicle(fromDate,toDate,vehicleID);
    }
}
