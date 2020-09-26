package com.osc.lenus.LenusBackend.payload.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDot {
    private String fullName;
    private String reservationCode;
}
