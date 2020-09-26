package com.osc.lenus.LenusBackend.model.local;

import com.osc.lenus.LenusBackend.model.central.Zone;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Pools")
@Data
public class Pool extends Zone {
    @DBRef
    private List<Event> events;

    private String openingTime;

    private String closingTime;
}
