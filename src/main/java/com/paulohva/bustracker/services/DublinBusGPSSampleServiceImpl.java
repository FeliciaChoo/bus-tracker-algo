package com.paulohva.bustracker.services;

import com.paulohva.bustracker.domain.DublinBusGPSSample;
import com.paulohva.bustracker.dto.Operator;
import com.paulohva.bustracker.dto.VehicleID;
import com.paulohva.bustracker.repository.DublinBusGPSSampleRepository;
import com.paulohva.bustracker.services.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DublinBusGPSSampleServiceImpl implements DublinBusGPSSampleService{

    private DublinBusGPSSampleRepository repo;

    private MongoTemplate mongoTemplate;

    public DublinBusGPSSampleServiceImpl(DublinBusGPSSampleRepository repo, MongoTemplate mongoTemplate) {
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }

    public DublinBusGPSSample findById(String id) {
        Optional<DublinBusGPSSample> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<DublinBusGPSSample> findByTimestampBetween(long fromDate, long toDate) {
        return repo.findByTimestampBetween(fromDate, toDate);
    }

    @Override
    public List<Operator> findAllOperatorsByTimeFrame(long fromDate, long toDate) {
        //return repo.findOperatorDistinctByTimestampBetween(fromDate,toDate);

        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp").lte(toDate)),
                match(Criteria.where("timestamp").gte(fromDate)),
                group("operator")

        );

        //Convert the aggregation result into a List
        AggregationResults<Operator> groupResults
                = mongoTemplate.aggregate(agg, DublinBusGPSSample.class, Operator.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<VehicleID> findVehiclesByTimeFrameAndOperator(long fromDate, long toDate, String operator) {
        //return repo.findOperatorDistinctByTimestampBetween(fromDate,toDate);

        Aggregation agg = newAggregation(
                match(Criteria.where("timestamp").lte(toDate)),
                match(Criteria.where("timestamp").gte(fromDate)),
                match(Criteria.where("operator").is(operator)),
                group("vehicleID")

        );

        //Convert the aggregation result into a List
        AggregationResults<VehicleID> groupResults
                = mongoTemplate.aggregate(agg, DublinBusGPSSample.class, VehicleID.class);
        return groupResults.getMappedResults();
    }


}
