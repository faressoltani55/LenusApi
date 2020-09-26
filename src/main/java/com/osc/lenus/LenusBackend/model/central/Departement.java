package com.osc.lenus.LenusBackend.model.central;

import com.osc.lenus.LenusBackend.model.local.HotelResponsible;
import com.osc.lenus.LenusBackend.model.local.Staff;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Departments")
@Data
public class Departement {
    @Id
    private String id;

    private String name;

    @DBRef
    private List<HotelResponsible> responsibles;
    @DBRef
    private List<Staff> staff;
}
