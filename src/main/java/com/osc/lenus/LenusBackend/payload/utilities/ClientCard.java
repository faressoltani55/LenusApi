package com.osc.lenus.LenusBackend.payload.utilities;


import com.osc.lenus.LenusBackend.model.central.Client;
import com.osc.lenus.LenusBackend.model.central.Preference;
import com.osc.lenus.LenusBackend.model.central.Feedback;
import com.osc.lenus.LenusBackend.model.central.Request;
import com.osc.lenus.LenusBackend.model.local.Food;
import com.osc.lenus.LenusBackend.model.local.Item;
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

    private List<String> primordials = new ArrayList<>();
    private List<String> essentials = new ArrayList<>();
    private List<String> others = new ArrayList<>();

    private List<String> reviews = new ArrayList<>();

    private List<String> perchases = new ArrayList<>();

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
        for(Preference preference : client.getPrimordials())
            this.primordials.addAll(preference.getValues());
        for(Preference preference : client.getEssentials())
            this.essentials.addAll(preference.getValues());
        for(Preference preference : client.getOthers())
            this.others.addAll(preference.getValues());
        for(Feedback feedback : client.getFeedbacks())
            this.reviews.add(feedback.getText());
        for(Item item : reservation.getPurchases())
            this.perchases.add(item.getName());
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
