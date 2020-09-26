package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "Events")
@Data
public class Event {
    @Id
    private String id;
    private String name;
    private String imgUrl;
    private String type;
    private int numberMax;
    private int numberReserved;
    private String date;
    private String startTime;
    private String endTime;
}
