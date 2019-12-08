package com.paulohva.bustracker.services;

import com.paulohva.bustracker.domain.DublinBusGPSSample;
import com.paulohva.bustracker.dto.Operator;
import com.paulohva.bustracker.dto.VehicleID;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DublinBusGPSSampleService {
    public DublinBusGPSSample findById(String id);

    public List<DublinBusGPSSample> findByTimestampBetween(long fromDate, long toDate);

    public List<Operator> findAllOperatorsByTimeFrame(long fromDate, long toDate);

    public List<VehicleID> findVehiclesByTimeFrameAndOperator(long fromDate, long toDate, String operator);
}
