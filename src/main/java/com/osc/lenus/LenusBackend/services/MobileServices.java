package com.osc.lenus.LenusBackend.services;

import com.osc.lenus.LenusBackend.model.central.*;
import com.osc.lenus.LenusBackend.model.local.*;
import com.osc.lenus.LenusBackend.payload.requests.Profile;
import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.repositories.central.*;
import com.osc.lenus.LenusBackend.repositories.local.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MobileServices {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RecommandationRepository recommandationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SpaRepository spaRepository;

    @Autowired
    private RequestsRepository requestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private AddressRepository addressRepository;


    public boolean enter(String reservationCode, String clientId) {
        return this.reservationRepository.existsByReservationCodeAndClientId(reservationCode,clientId);
    }

    public List<Occupation> getOccupation() {
        List<Zone> hotelZones = new ArrayList<>();
        hotelZones.addAll(this.restaurantRepository.findAll());
        hotelZones.addAll(this.barRepository.findAll());
        hotelZones.addAll(this.receptionRepository.findAll());
        hotelZones.addAll(this.gymRepository.findAll());
        hotelZones.addAll(this.poolRepository.findAll());
        hotelZones.addAll(this.spaRepository.findAll());
        List<Occupation> occupations = new ArrayList<>();
        Date oneMinBack = new Date(System.currentTimeMillis() - 60 * 1000);
        for(int i=0; i<hotelZones.size(); i++) {
            Zone zone = hotelZones.get(i);
            zone.setOccupation(0);
            List<Beacon> beacons = zone.getBeacons();
            List<Access> accesses = new ArrayList<>();
            for(Beacon beacon : beacons) {
                accesses.addAll(this.accessRepository.findByBeaconId(beacon.getId(),oneMinBack));
            }
            zone.setOccupation(accesses.size());
            occupations.add(new Occupation(zone.getName(),(double) zone.getOccupation() / zone.getSize()));
        }
        return occupations;
    }


    public List<Recommandation> getRecommendations(String resCode) {
        return this.recommandationRepository.findByResCode(resCode);
    }


    public void updateProfile(String resCode, Profile profile) {
        Client client = this.clientRepository.findById(this.reservationRepository.findByReservationCode(resCode).getClientId()).get();
        client.setLastName(profile.getLastName());
        client.setFirstName(profile.getFirstName());
        client.setEmail(profile.getEmail());
        Address address = client.getAddress();
        if(address == null) address = new Address();
        address.setCountry(profile.getCountry());
        this.addressRepository.save(address);
        client.setAddress(address);
        this.clientRepository.save(client);
    }

    // in the front, notify with x, if x equal to "" do not do anything
    public String getRequestStatus(String resCode) {
        Request request = this.requestRepository.findByResCodeOrderByIdDesc(resCode).get(0);
        if(request.getStatus().equals("Pending"))
            return "";
        else if(request.getStatus().equals("Processing"))
            return "Your request is being processed !";
        else
            return "Your request was treated, please feel free to reach us if your need something else !";
    }

    public void makeRequest(String resCode, String text) {
        Request request = new Request();
        Reservation reservation = this.reservationRepository.findByReservationCode(resCode);
        Room room = this.roomRepository.findByHotelIdAndNumber(reservation.getHotelId(),reservation.getRoomNumber());
        request.setName(reservation.getFirstname() + " " + reservation.getLastname());
        request.setResCode(resCode);
        request.setText(text);
        request.setHotelId(reservation.getHotelId());
        request.setDepartmentId("");
        request.setRoomNumber(reservation.getRoomNumber());
        request.setFloor(room.getFloor());
        request.setStatus("Pending");
        this.requestRepository.save(request);
    }

    public void makeFeedback(String resCode, Feedback feedback) {
        feedback.setHotelId(this.reservationRepository.findByReservationCode(resCode).getHotelId());
        this.feedbackRepository.save(feedback);
    }

    public void buy(String resCode, Item item) {
        Reservation reservation = this.reservationRepository.findByReservationCode(resCode);
        reservation.setFees(reservation.getFees()+item.getPrice());
        List<Item> items = reservation.getPurchases();
        items.add(item);
        this.itemsRepository.save(item);
        this.reservationRepository.save(reservation);
    }

    public List<Item> getPurchases(String resCode) {
        return this.reservationRepository.findByReservationCode(resCode).getPurchases();
    }


}
