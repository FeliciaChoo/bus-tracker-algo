package com.paulohva.bustracker.repository;


import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.Fleet;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.dto.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DublinkedDataRepo extends MongoRepository<DublinkedData, String> {

    // Get the latest data for a given vehicleID
    @Query(value = "{ 'vehicleID' : ?0 }", sort = "{ 'timestamp' : -1 }")
    Optional<DublinkedData> findLatestByVehicleID(int vehicleID);

    List<Vehicle> findVehiclesByOperator(long fromDate, long toDate, String operator, boolean onlyAtStop);

    List<Fleet> findOperators(long fromDate, long toDate);

    List<Trace> findTraceByVehicle(long fromDate, long toDate, int vehicleID);

    List<DublinkedData> findTraceByVehicle(int vehicleID);

    Optional<DublinkedData> findTopByVehicleIDOrderByTimestampDesc(int vehicleID);
}
