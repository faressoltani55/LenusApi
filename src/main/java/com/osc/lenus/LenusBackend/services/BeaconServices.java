package com.osc.lenus.LenusBackend.services;


import com.osc.lenus.LenusBackend.model.central.Hotel;
import com.osc.lenus.LenusBackend.model.local.Access;
import com.osc.lenus.LenusBackend.model.local.Contact;
import com.osc.lenus.LenusBackend.model.local.Reservation;
import com.osc.lenus.LenusBackend.model.local.Temperature;
import com.osc.lenus.LenusBackend.payload.requests.BContact;
import com.osc.lenus.LenusBackend.repositories.central.HotelsRepository;
import com.osc.lenus.LenusBackend.repositories.local.AccessRepository;
import com.osc.lenus.LenusBackend.repositories.local.ContactRepository;
import com.osc.lenus.LenusBackend.repositories.local.ReservationRepository;
import com.osc.lenus.LenusBackend.repositories.local.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeaconServices {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private AccessRepository accessRepository;

    public void saveContact(BContact bContact) {
        Reservation r1 = reservationRepository.findByBraceletId(bContact.getIdBracelet1(), bContact.getDate());
        Reservation r2 = reservationRepository.findByBraceletId(bContact.getIdBracelet2(), bContact.getDate());
        if(!contactRepository.existsByIdPerson1AndIdPerson2(r1.getReservationCode(),r2.getReservationCode()) && !contactRepository.existsByIdPerson1AndIdPerson2(r2.getReservationCode(),r1.getReservationCode())) {
            Contact contact = new Contact();
            contact.setIdPerson1(r1.getReservationCode());
            contact.setIdPerson2(r2.getReservationCode());
            contact.setNamePerson1(r1.getFirstname()+ " " + r1.getLastname());
            contact.setNamePerson2(r2.getFirstname()+ " " + r2.getLastname());
            this.contactRepository.save(contact);
            Hotel hotel = this.hotelsRepository.findById(this.reservationRepository.findByReservationCode(r1.getReservationCode()).getHotelId()).get();
            List<Contact> contacts = hotel.getContacts();
            contacts.add(contact);
            hotel.setContacts(contacts);
            this.hotelsRepository.save(hotel);
        }
    }

    public void saveTemperatureAlert(Temperature tmp) {
        Reservation reservation = reservationRepository.findByBraceletId(tmp.getBraceletId(), tmp.getDate());
        List<Temperature> tmps = reservation.getTemperatureMeasurements();
        tmps.add(tmp);
        reservation.setTemperatureMeasurements(tmps);
        this.temperatureRepository.save(tmp);
        this.reservationRepository.save(reservation);
    }

    public void saveAccess(Access access) {
        this.accessRepository.save(access);
    }
}
