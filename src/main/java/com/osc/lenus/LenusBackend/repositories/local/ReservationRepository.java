package com.osc.lenus.LenusBackend.repositories.local;

import com.osc.lenus.LenusBackend.model.local.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation,String> {


    @Query("{'braceletId' : ?0, 'arrivalDate' : {$lte : ?1}, 'exitDate' : {$gte : ?1}}")
    public Reservation findByBraceletId(String id, Date date);

    @Query("{'arrivalDate' : {$lte : ?0}, 'exitDate' : {$gte : ?0}}")
    public List<Reservation> findReservationsByDate(String date);

    public Reservation findByClientIdAndArrivalDateAndExitDate(String clientId, Date arrivalDate, Date exitDate);

    public List<Reservation> findByHotelId(String hotelId);

    public Reservation findByReservationCode(String reservationCode);

    public List<Reservation> findByGroupCode(String groupCode);

    public List<Reservation> findByRoomNumber(int roomNumber);
}
