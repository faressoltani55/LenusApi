package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends MongoRepository<Item,String> {
}
