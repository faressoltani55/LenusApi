package com.osc.lenus.LenusBackend.payload.requests;

import lombok.Data;

import java.util.Date;

@Data
public class BContact {
    private String idBracelet1;
    private String idBracelet2;
    private Date date;
}
