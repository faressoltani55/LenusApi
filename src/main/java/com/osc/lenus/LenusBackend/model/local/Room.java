package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Rooms")
@Data
public class Room {
    @Id
    private String id;
    private int number = 0;
    private int size = 0;
    private int floor = 0;
    private boolean occupied = false;
    private String hotelId;
    private List<String> clientNames = new ArrayList<>();
}
