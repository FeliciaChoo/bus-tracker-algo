package com.paulohva.bustracker.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.paulohva.bustracker.domain.DublinkedData;
import com.paulohva.bustracker.dto.Fleet;
import com.paulohva.bustracker.dto.Trace;
import com.paulohva.bustracker.dto.Vehicle;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class DublinkedDataRepoImpl implements DublinkedDataCustom {
    private final MongoTemplate mongoTemplate;

    public DublinkedDataRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Fleet> findOperators(long fromDate, long toDate) {
        //Build a aggregation operation with the search conditions
        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp").lte(toDate)),
                match(Criteria.where("timestamp").gte(fromDate)),
                group("operator")
        );

        //Convert the aggregation result into a List
        AggregationResults<Fleet> groupResults
                = mongoTemplate.aggregate(agg, DublinkedData.class, Fleet.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<Vehicle> findVehiclesByOperator(long fromDate, long toDate, String operator, boolean onlyAtStop) {
        //Build a aggregation operation list with the search conditions
        List<AggregationOperation> operations = new ArrayList<AggregationOperation>();
        operations.add(Aggregation.match(Criteria.where("timestamp").gte(fromDate).lte(toDate)));
        operations.add(Aggregation.match(Criteria.where("operator").is(operator)));
        if (onlyAtStop) {
            operations.add(Aggregation.match(Criteria.where("atStop").is(1)));
        }
        operations.add(Aggregation.group("vehicleID"));
        Aggregation agg = newAggregation(operations);

        //Convert the aggregation result into a List
        AggregationResults<Vehicle> groupResults
                = mongoTemplate.aggregate(agg, DublinkedData.class, Vehicle.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<Trace> findTraceByVehicle(long fromDate, long toDate, int vehicleID) {
        return new ArrayList<>();
    }


    public List<Trace> findTraceByVehicle(int vehicleID) {
        // Match only vehicle ID, no time filtering
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("vehicleID").is(vehicleID)),
                Aggregation.sort(Sort.Direction.ASC, "timestamp")
        );

        AggregationResults<Trace> results =
                mongoTemplate.aggregate(agg, DublinkedData.class, Trace.class);

        return results.getMappedResults();
    }
}
