package com.osc.lenus.LenusBackend.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contacted {
    private String fullName;
    private int roomNumber;
    private long mobile;
}
