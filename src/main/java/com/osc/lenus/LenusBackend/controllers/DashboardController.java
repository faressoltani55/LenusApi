package com.osc.lenus.LenusBackend.controllers;

import com.osc.lenus.LenusBackend.model.central.Feedback;
import com.osc.lenus.LenusBackend.model.central.Request;
import com.osc.lenus.LenusBackend.model.local.Room;
import com.osc.lenus.LenusBackend.payload.requests.Group;
import com.osc.lenus.LenusBackend.payload.responses.ContactGraph;
import com.osc.lenus.LenusBackend.payload.responses.Contacted;
import com.osc.lenus.LenusBackend.payload.utilities.ClientCard;
import com.osc.lenus.LenusBackend.payload.utilities.ContactDot;
import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.payload.utilities.TemperatureCard;
import com.osc.lenus.LenusBackend.services.HotelResponsibleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
public class DashboardController {
    @Autowired
    private HotelResponsibleServices hotelResponsibleServices;

    // Dashboard endpoints :

    //success !
    @GetMapping("dashboard/{hotelName}/occupations")
    public List<Occupation> getOccupation(@PathVariable String hotelName) {
        return this.hotelResponsibleServices.getOccupation(hotelName);
    }

    @GetMapping("dashboard/{hotelName}/graph")
    public ContactGraph getGraphInfo(@PathVariable String hotelName) {
        return new ContactGraph(this.hotelResponsibleServices.getDots(hotelName), this.hotelResponsibleServices.getContactLines(hotelName));
    }

    @GetMapping("dashboard/{hotelName}/requests")
    public List<Request> getRequests(@PathVariable String hotelName) {
        return this.hotelResponsibleServices.getRequests(hotelName);
    }

    // Clients endpoints :

    //success !
    @GetMapping("dashboard/{hotelName}/clients")
    public List<ClientCard> getClientsList(@PathVariable String hotelName) {
        return this.hotelResponsibleServices.getClients(hotelName);
    }

    //success !
    @PostMapping(value = "dashboard/{hotelName}/clients")
    public void createReservation(@PathVariable String hotelName, @RequestBody ClientCard clientCard) {
        this.hotelResponsibleServices.createReservation(hotelName,clientCard);
    }

    //success !
    @PatchMapping("dashboard/{hotelName}/clients")
    public void updateReservation(@RequestBody ClientCard clientCard) {
        this.hotelResponsibleServices.updateReservation(clientCard);
    }

    // Reservations endpoints :

    //success !
    @GetMapping("dashboard/{hotelName}/group")
    public List<ContactDot> getPeople() {
        return this.hotelResponsibleServices.getPeople();
    }

    //success !
    @PostMapping("dashboard/{hotelName}/group")
    public void setGroup(@RequestBody Group group) throws IOException, MessagingException {
        this.hotelResponsibleServices.setGroup(group);
    }

    //success !
    @GetMapping("dashboard/{hotelName}/roommates")
    public List<ContactDot> getRoommates() {
        return this.hotelResponsibleServices.getRoommates();
    }

    //success !
    @PostMapping("dashboard/{hotelName}/group/{room}")
    public void setRoom(@PathVariable int room, @RequestBody Group group) {
        this.hotelResponsibleServices.setRoom(group, room);
    }

    // success
    @GetMapping("dashboard/{hotelName}/group/rooms")
    public List<Room> getRoom(@PathVariable String hotelName) {
        return this.hotelResponsibleServices.getRooms(hotelName);
    }

    // Requests endpoints :


    @PatchMapping("dashboard/{hotelName}/requests")
    public void changeRequestStatus(@RequestBody Request request) {
        this.hotelResponsibleServices.changeRequestStatus(request);
    }

    // Feedbacks endpoints :

    @GetMapping("dashboard/{hotelName}/feedbacks")
    public List<Feedback> getFeedbacks(@PathVariable String hotelName) {
        return this.hotelResponsibleServices.getFeedbacks(hotelName);
    }

    // Health endpoints :

    @GetMapping("dashboard/{hotelName}/temperatures")
    public List<TemperatureCard> getTemperatureAlerts() {
        return this.hotelResponsibleServices.getTemperatureAlerts();
    }

    @GetMapping("dashboard/{hotelName}/contacts/{resCode}")
    public List<Contacted> getContactCircle(@PathVariable String resCode) {
        return this.hotelResponsibleServices.getContactCircle(resCode);
    }

}
