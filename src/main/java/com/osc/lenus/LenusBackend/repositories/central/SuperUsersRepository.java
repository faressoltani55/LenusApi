package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.SuperUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperUsersRepository extends MongoRepository<SuperUser,String> {
    public void deleteByEmail(String email);
    Optional<SuperUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
