package com.example.SecurityApp.request;

import com.example.SecurityApp.model.Role;
import lombok.Data;

@Data
public class UserUpdateRequest {

    private  String username;

    private String password;

    private Role role;
}
