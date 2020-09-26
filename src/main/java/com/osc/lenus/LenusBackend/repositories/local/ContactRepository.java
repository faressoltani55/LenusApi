package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<Contact,String> {
    public boolean existsByIdPerson1AndIdPerson2(String id1, String id2);
    public List<Contact> findByIdPerson1(String id1);
    public List<Contact> findByIdPerson2(String id2);
}
