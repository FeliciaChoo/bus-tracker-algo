package com.paulohva.bustracker.services;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.Fleet;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.dto.Vehicle;
import com.paulohva.bustracker.repository.DublinkedDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DublinkedDataServiceImpl implements DublinkedDataService {

    private static final Logger log = LoggerFactory.getLogger(DublinkedDataServiceImpl.class);

    private final DublinkedDataRepo repo;

    public DublinkedDataServiceImpl(DublinkedDataRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Vehicle> findVehiclesByOperator(long fromDate, long toDate, String operator, boolean onlyAtStop) {
        log.info("findVehiclesByOperator: operator={}, onlyAtStop={}, fromDate={}, toDate={}", operator, onlyAtStop, fromDate, toDate);
        return repo.findVehiclesByOperator(fromDate, toDate, operator, onlyAtStop);
    }

    @Override
    public List<Fleet> findOperators(long fromDate, long toDate) {
        log.info("findOperators: fromDate={}, toDate={}", fromDate, toDate);
        return repo.findOperators(fromDate, toDate);
    }

    @Override
    public List<Trace> findTraceByVehicle(long fromDate, long toDate, int vehicleID) {
        log.info("findTraceByVehicle (with date): vehicleID={}, fromDate={}, toDate={}", vehicleID, fromDate, toDate);
        return repo.findTraceByVehicle(fromDate, toDate, vehicleID);
    }

    @Override
    public List<DublinkedData> findTraceByVehicle(int vehicleID) {
        log.info("findTraceByVehicle: vehicleID={}", vehicleID);
        return repo.findTraceByVehicle(vehicleID);
    }
}
