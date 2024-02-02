package com.example.SecurityApp.service;


import com.example.SecurityApp.model.AuthenticationRequest;
import com.example.SecurityApp.model.AuthenticationResponse;
import com.example.SecurityApp.model.User;
import com.example.SecurityApp.repository.UserRepository;
import com.example.SecurityApp.request.UserRegistrationRequest;
import com.example.SecurityApp.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public User saveUser(UserRegistrationRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());

        return userRepository.save(user);
    }
    public AuthenticationResponse userLogin(@RequestBody AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).get();
    }
    public User updateUser(int id, UserUpdateRequest UpdateRequest){
        User toUpdate = getUserById(id);
        toUpdate.setUsername(UpdateRequest.getUsername());
        toUpdate.setPassword(UpdateRequest.getPassword());
        toUpdate.setRole(UpdateRequest.getRole());

        return userRepository.save(toUpdate);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }
}
