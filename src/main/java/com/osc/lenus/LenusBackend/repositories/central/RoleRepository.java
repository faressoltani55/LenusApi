package com.osc.lenus.LenusBackend.repositories.central;


import com.osc.lenus.LenusBackend.security.models.ERole;
import com.osc.lenus.LenusBackend.security.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
