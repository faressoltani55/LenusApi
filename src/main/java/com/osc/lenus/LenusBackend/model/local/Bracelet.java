package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bracelets")
@Data
public class Bracelet {
    @Id
    private String id;
    private String hotelId;
    private boolean taken;
}
