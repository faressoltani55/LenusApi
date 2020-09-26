package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client,String> {
    Optional<Client> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Client findByEmail(String email);
}
