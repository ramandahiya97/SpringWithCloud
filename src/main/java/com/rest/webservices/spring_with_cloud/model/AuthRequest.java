package com.rest.webservices.spring_with_cloud.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    // Getters and setters

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
