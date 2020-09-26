package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Reservations")
@Data
public class Reservation {
    @Id
    private String id;
    private String firstname = "";
    private String lastname = "";


    private String clientId = ""; // null in case of a child

    private String reservationCode = "";
    private String groupCode = "";
    private long mobile = 0;
    private int roomNumber = 0;
    private boolean adult = false;
    private boolean verifiedId = false;
    private String hotelId = "";
    private Date arrivalDate = new Date();
    private Date exitDate = new Date();
    private double fees;

    private String braceletId = "";

    @DBRef
    private List<Access> accessSignals = new ArrayList<>();

    @DBRef
    private List<Temperature> temperatureMeasurements = new ArrayList<>();

    @DBRef
    private List<Recommandation> recommandations = new ArrayList<>();

    @DBRef
    private List<Food> purchases = new ArrayList<>();

}
