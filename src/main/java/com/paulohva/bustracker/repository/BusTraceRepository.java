package com.paulohva.bustracker.repository;

import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.Trace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BusTraceRepository extends MongoRepository<DublinkedData, String> {
    List<DublinkedData> findByVehicleID(int vehicleID);  // Use String if vehicleID is stored as String
    Optional<DublinkedData> findFirstByVehicleIDOrderByTimestampDesc(int vehicleID);

    List<DublinkedData> findByVehicleIDAndTimestampBetween(int vehicleID, long start, long end);
}