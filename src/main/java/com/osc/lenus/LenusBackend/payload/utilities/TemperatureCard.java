package com.osc.lenus.LenusBackend.payload.utilities;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TemperatureCard implements Comparable<TemperatureCard> {
    private String id;
    private double temperature;
    private String resCode;
    private Date date;
    private int roomNumber;
    private String fullName;

    @Override
    public int compareTo(TemperatureCard temperature) {
        return this.getId().compareTo(temperature.getId());
    }
}
