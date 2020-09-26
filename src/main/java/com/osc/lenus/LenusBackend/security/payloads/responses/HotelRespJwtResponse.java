package com.osc.lenus.LenusBackend.security.payloads.responses;

import lombok.Data;

@Data
public class HotelRespJwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String deptId;
    private String username;
    private String fullName;
    private String title;
    private String hotelName;
    private String imgUrl;

    public HotelRespJwtResponse(String token, String id, String deptId, String username, String hotelName, String fullName, String title, String imgUrl) {
        this.token = token;
        this.id = id;
        this.deptId = deptId;
        this.username = username;
        this.hotelName = hotelName;
        this.fullName = fullName;
        this.title = title;
        this.imgUrl = imgUrl;
    }
}
