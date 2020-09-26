package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.Access;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccessRepository extends MongoRepository<Access,String> {
    @Query("{beaconId : ?0, date: { $gt: ?1 }}")
    public int countByBeaconId(String beaconId, Date oneMinuteBack);

    @Query("{beaconId : ?0, date: { $gt: ?1 }}")
    public List<Access> findByBeaconId(String beaconId, Date oneMinuteBack);

}
