package com.osc.lenus.LenusBackend.payload.requests;

import lombok.Data;

@Data
public class Profile {
    String firstName;
    String lastName;
    String email;
    String country;
}
