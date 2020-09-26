package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepository extends MongoRepository<Hotel,String> {
    public Hotel findByName(String name);
    public Hotel findByNameAndChainName(String name, String chainName);
    public boolean existsByName(String name);
    public void deleteByNameAndChainName(String name, String chainName);
}
