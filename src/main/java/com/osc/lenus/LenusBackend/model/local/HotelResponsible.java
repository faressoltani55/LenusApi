package com.osc.lenus.LenusBackend.model.local;

import com.osc.lenus.LenusBackend.model.central.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document(collection = "HotelResponsible")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponsible {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    @Email
    @Indexed(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String imgUrl;

    @Indexed(unique = true)
    private int cin;

    private String chainName;

    private String hotelName;

    private String departmentId; // Liste des départements de l'hôtel

    private String zoneId; // La zone où il travaille

    private String title; // role

    @DBRef
    private Bracelet bracelet;

    public HotelResponsible(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
