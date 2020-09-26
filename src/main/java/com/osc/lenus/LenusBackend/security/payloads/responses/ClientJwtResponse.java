package com.osc.lenus.LenusBackend.security.payloads.responses;

import com.osc.lenus.LenusBackend.model.central.Client;
import lombok.Data;

import java.util.List;


@Data
public class ClientJwtResponse {
    private String id;
    private String token;
    private String type = "Bearer";
    private String email;
    private String firstName;
    private String lastName;
    private String imgUrl;
    private String cin;
    private String passport;
    private String birthDate;
    private String country;
    private List<String> roles;

    public ClientJwtResponse(String accessToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public ClientJwtResponse(String accessToken, Client client, List<String> roles) {
        this.token = accessToken;
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.imgUrl = client.getImgUrl();
        this.cin = client.getIdCard();
        this.passport = client.getPassport();
        this.birthDate = client.getBirthDate();
        this.country = "";
        this.roles = roles;
    }

}
