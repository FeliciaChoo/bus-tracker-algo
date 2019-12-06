package com.paulohva.bustracker.resources;

import com.paulohva.bustracker.domain.DublinBusGPSSample;
import com.paulohva.bustracker.dto.StandardDto;
import com.paulohva.bustracker.services.DublinBusGPSSampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value="/bustracker")
public class BusTrackerResource {

    private DublinBusGPSSampleService service;

    public BusTrackerResource(DublinBusGPSSampleService service) {
        this.service = service;
    }

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public ResponseEntity<DublinBusGPSSample> findById(@PathVariable String id) {
        DublinBusGPSSample obj = service.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<List<DublinBusGPSSample>> insert(@RequestBody StandardDto objDto) {
        //TODO PV Criar converter
        long fromDate = TimeUnit.SECONDS.toMicros(objDto.getFromDate().toEpochSecond());
        long toDate = TimeUnit.SECONDS.toMicros(objDto.getToDate().toEpochSecond());
        List<DublinBusGPSSample> objReturn = service.findByTimestampBetween(fromDate, toDate);
        return ResponseEntity.ok().body(objReturn);
    }

}
