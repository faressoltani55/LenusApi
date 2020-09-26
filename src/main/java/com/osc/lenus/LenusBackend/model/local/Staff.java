package com.osc.lenus.LenusBackend.model.local;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Staff")
@Data
public class Staff {
    @Id
    private String id;

    private String departmentId;

    private String zoneId;

    @DBRef
    private Bracelet bracelet;
}
