package com.osc.lenus.LenusBackend.payload.utilities;


import com.osc.lenus.LenusBackend.model.central.Client;
import com.osc.lenus.LenusBackend.model.central.Preference;
import com.osc.lenus.LenusBackend.model.central.Feedback;
import com.osc.lenus.LenusBackend.model.central.Request;
import com.osc.lenus.LenusBackend.model.local.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCard {

    private String email;
    private String firstName;
    private String lastName;
    private String imgUrl;
    private String cin;
    private String passport;
    private String birthDate;
    private String country;
    private Date exitDate;
    private Date arrivalDate;
    private boolean adult;

    private List<String> preferences = new ArrayList<>();

    private List<String> reviews = new ArrayList<>();

    private List<String> requests = new ArrayList<>();

    public ClientCard(Reservation reservation, Client client) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        this.email = client.getEmail();
        this.firstName = reservation.getFirstname();
        this.lastName = reservation.getLastname();
        this.cin = client.getIdCard();
        this.passport = client.getPassport();
        this.arrivalDate = reservation.getArrivalDate();
        this.exitDate = reservation.getExitDate();
        this.adult = reservation.isAdult();
        this.imgUrl = client.getImgUrl();
        this.birthDate = client.getBirthDate();
        this.country = client.getAddress().getCountry();
        for(Preference preference : client.getPreferences())
            this.preferences.addAll(preference.getValues());
        for(Feedback feedback : client.getFeedbacks())
            this.reviews.add(feedback.getText());
        for(Request request : client.getRequests())
            this.requests.add(request.getText());
    }

    public ClientCard(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.imgUrl = client.getImgUrl();
        this.cin = client.getIdCard();
        this.passport = client.getPassport();
        this.birthDate = client.getBirthDate();
        this.country = client.getAddress().getCountry();
    }
}
