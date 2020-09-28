package com.osc.lenus.LenusBackend.repositories.central;

import com.osc.lenus.LenusBackend.model.central.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestsRepository extends MongoRepository<Request, String> {
    public List<Request> findByDepartmentId(String departmentId);
    public List<Request> findByHotelIdAndFloor(String departmentId, int floor);
    public List<Request> findByHotelId(String hotelId);
    public List<Request> findByResCodeOrderByIdDesc(String resCode);
}
