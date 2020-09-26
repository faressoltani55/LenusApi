package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.HotelResponsible;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelResponsibleRepository extends MongoRepository<HotelResponsible, String> {
    HotelResponsible findByEmail(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    public List<HotelResponsible> findByChainNameAndHotelName(String chainName, String hotelName);
    public void deleteByUsername(String username);
}
