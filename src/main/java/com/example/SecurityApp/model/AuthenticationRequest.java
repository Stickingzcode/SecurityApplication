package com.example.SecurityApp.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationRequest {
    private String password;
    private String username;
}
