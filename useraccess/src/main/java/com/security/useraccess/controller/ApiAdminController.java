package com.security.useraccess.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.security.useraccess.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.security.useraccess.entity.User;

@RestController
@RequestMapping("/api/admin")
public class ApiAdminController {
    private final UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;

    public ApiAdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //requires sending the JWT token on authorization header
    //Bearer token
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        System.out.println("Entered");
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User aux=userRepository.findById(id).get();
        aux.removeRole();
        userRepository.delete(aux);
        return ResponseEntity.ok("User deleted");
    }
}