package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.Departement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Departement, String> {
}
