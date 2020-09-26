package com.osc.lenus.LenusBackend.security.payloads.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SignupRequest {

    private String username;

    @NotBlank
    private String email;


    @NotBlank
    private String password;
}
