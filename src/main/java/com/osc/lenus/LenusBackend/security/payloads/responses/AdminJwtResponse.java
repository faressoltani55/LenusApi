package com.osc.lenus.LenusBackend.security.payloads.responses;

import java.util.List;

public class AdminJwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String email;
    private String username;
    private List<String> roles;

    public AdminJwtResponse(String accessToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
