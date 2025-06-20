package com.paulohva.bustracker.repository;

import com.paulohva.bustracker.model.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StopRepository extends MongoRepository<Stop, String> {

    @Query("{ 'demand' : { $gt: 0 } }")
    List<Stop> findAllWithDemand();

    @Query(value = "{ location : { $nearSphere : { $geometry : { type : 'Point', coordinates : [ ?0, ?1 ] } } } }",
            sort = "{ location : 1 }")
    List<Stop> findNearestStops(double longitude, double latitude);
}