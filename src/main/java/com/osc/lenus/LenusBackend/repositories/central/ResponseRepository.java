package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends MongoRepository<Response,String> {
}
