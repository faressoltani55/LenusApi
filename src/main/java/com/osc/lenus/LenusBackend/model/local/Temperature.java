package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Temperature")
@Data
public class Temperature {
    @Id
    private String id;

    private String braceletId;
    private double temperature;
    private Date date;
}
