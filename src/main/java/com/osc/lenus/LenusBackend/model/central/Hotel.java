package com.osc.lenus.LenusBackend.model.central;

import com.osc.lenus.LenusBackend.model.local.Contact;
import com.osc.lenus.LenusBackend.model.local.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Hotel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private int openingYear;

    private int joiningDate;

    private String chainName;

    @DBRef
    private List<Departement> departements;


    private List<String> rooms;

    @DBRef
    private List<Zone> zones;

    private List<String> bracelets;

    @DBRef
    private Address address;

    @DBRef
    private List<Contact> contacts;
}
