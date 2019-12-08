package com.paulohva.bustracker.repository;

import com.paulohva.bustracker.domain.DublinBusGPSSample;
import com.paulohva.bustracker.dto.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DublinBusGPSSampleRepository extends MongoRepository<DublinBusGPSSample, String> {

    List<DublinBusGPSSample> findByOperator(String text);

    List<DublinBusGPSSample> findByTimestampBetween(long timestampGT, long timestampLT);

    @Query("[ { $match: { timestamp: { $gt: 70, $lt: 90 } } }, { $group: { _id : \"$operator\" } }]")
    List<Operator> findAllOperatorsByTimeframe(long timestampGT, long timestampLT);
}
