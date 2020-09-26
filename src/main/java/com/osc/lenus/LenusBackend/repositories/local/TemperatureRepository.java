package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends MongoRepository<Temperature,String> {
}
