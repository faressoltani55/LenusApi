package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends MongoRepository<Staff,String> {
}
