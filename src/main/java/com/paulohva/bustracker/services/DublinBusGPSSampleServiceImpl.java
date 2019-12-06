package com.paulohva.bustracker.services;

import com.paulohva.bustracker.domain.DublinBusGPSSample;
import com.paulohva.bustracker.repository.DublinBusGPSSampleRepository;
import com.paulohva.bustracker.services.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DublinBusGPSSampleServiceImpl implements DublinBusGPSSampleService{

    private DublinBusGPSSampleRepository repo;

    public DublinBusGPSSampleServiceImpl(DublinBusGPSSampleRepository repo) {
        this.repo = repo;
    }

    public DublinBusGPSSample findById(String id) {
        Optional<DublinBusGPSSample> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<DublinBusGPSSample> findByTimestampBetween(long fromDate, long toDate) {
        return repo.findByTimestampBetween(fromDate, toDate);
    }
}
