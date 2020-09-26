package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Contacts")
@Data
public class Contact {
    @Id
    private String id;

    private String idPerson1;
    private String namePerson1;
    private String idPerson2;
    private String namePerson2;
    private String date;
}
