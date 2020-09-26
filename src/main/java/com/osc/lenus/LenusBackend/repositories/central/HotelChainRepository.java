package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.HotelsChain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelChainRepository extends MongoRepository<HotelsChain,String> {
    public boolean existsByName(String name);
    public void deleteByName(String name);
    public HotelsChain findByName(String name);
}
