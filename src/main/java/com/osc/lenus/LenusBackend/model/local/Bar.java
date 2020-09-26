package com.osc.lenus.LenusBackend.model.local;

import com.osc.lenus.LenusBackend.model.central.Zone;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Bars")
@Data
public class Bar extends Zone {
    // Food of type Drinks
    @DBRef
    private List<Food> drinks;

    @DBRef
    private List<Event> events;

    private String openingTime;

    private String closingTime;
}
