package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Items")
@Data
public class Item {
    @Id
    private String id;
    private String name;
    private String quantity;
    private double price;
}
