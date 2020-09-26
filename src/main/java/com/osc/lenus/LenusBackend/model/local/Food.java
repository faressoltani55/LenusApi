package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Food")
@Data
public class Food {
    @Id
    private String id;
    private String name;
    private String details;
    private String imgUrl;
    private String type; //Salty, Sweet, Drinks
    private double price;
}
