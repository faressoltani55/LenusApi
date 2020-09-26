package com.osc.lenus.LenusBackend.model.local;

import com.osc.lenus.LenusBackend.model.central.Zone;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Restaurants")
@Data
public class Restaurant extends Zone {
    @DBRef
    private List<Food> breakfast = new ArrayList<>();

    @DBRef
    private List<Food> lunch = new ArrayList<>();

    @DBRef
    private List<Food> dinner =  new ArrayList<>();

    private String openingTime;

    private String closingTime;

}
