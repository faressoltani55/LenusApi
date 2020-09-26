package com.osc.lenus.LenusBackend.model.central;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    private String id;

    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private long zipCode = 0;
}
