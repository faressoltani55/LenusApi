package com.osc.lenus.LenusBackend.model.central;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Requests")
@Data
public class Request {
    @Id
    private String id;
    private String hotelId;
    private String resCode;
    private String name;
    private String departmentId;
    private int roomNumber;
    private int floor;
    private String text;
    private String status;
}
