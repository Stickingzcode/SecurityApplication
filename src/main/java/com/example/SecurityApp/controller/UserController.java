package com.example.SecurityApp.controller;


import com.example.SecurityApp.model.AuthenticationRequest;
import com.example.SecurityApp.model.AuthenticationResponse;
import com.example.SecurityApp.model.User;
import com.example.SecurityApp.request.UserRegistrationRequest;
import com.example.SecurityApp.request.UserUpdateRequest;
import com.example.SecurityApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User saveUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return userService.saveUser(userRegistrationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse userLogin(@RequestBody AuthenticationRequest authenticationRequest){
        return userService.userLogin(authenticationRequest);
    }


    @GetMapping("/user")
    @PreAuthorize("ADMIN")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody UserUpdateRequest UpdateRequest){
        return userService.updateUser(id, UpdateRequest);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }
}
