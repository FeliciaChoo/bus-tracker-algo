package com.paulohva.bustracker.repository;

import com.paulohva.bustracker.domain.DublinkedData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DublinkedDataRepo extends MongoRepository<DublinkedData, String>, DublinkedDataCustom {

}
