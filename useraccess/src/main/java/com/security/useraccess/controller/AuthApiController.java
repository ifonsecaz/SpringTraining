package com.security.useraccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.useraccess.entity.*;
import com.security.useraccess.repository.UserRepository;
import com.security.useraccess.security.JwtUtil;
import com.security.useraccess.security.RoleService;


import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    private RoleService roleService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @Transactional
    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid User user) {
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        user.setPassword(encoder.encode(user.getPassword()));
        Role aux= roleService.verifyRole("USER");
        if(aux!=null){
            userRepository.save(user);
            user.setRole(aux);
            return "User registered successfully!";
        }
        return "Error";
    }

    @Transactional
    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody @Valid User user) {
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return "Error: Username is already taken!";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Role aux= roleService.verifyRole("ADMIN");
        if(aux!=null){
            userRepository.save(user);
            user.setRole(aux);
            return "User registered successfully!";
        }
        return "Error";
    }
}
