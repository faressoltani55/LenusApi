package com.osc.lenus.LenusBackend.payload.requests;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    List<String> reservationCodes;
}
