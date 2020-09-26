package com.osc.lenus.LenusBackend.model.central;


import com.osc.lenus.LenusBackend.model.local.Beacon;
import com.osc.lenus.LenusBackend.model.local.HotelResponsible;
import com.osc.lenus.LenusBackend.model.local.Staff;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Zones")
@Data
public abstract class Zone {
    @Id
    protected String id;
    protected String name;
    protected int size;
    protected int occupation;
    protected String hotelId;
    @DBRef
    protected HotelResponsible responsible;
    @DBRef
    protected List<Staff> staff;
    @DBRef
    protected List<Beacon> beacons;
}