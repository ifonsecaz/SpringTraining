package com.security.useraccess.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.security.useraccess.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.security.useraccess.entity.User;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    private final UserRepository userRepository;

    public ApiUserController(UserRepository userRepository) {
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


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User aux=userRepository.findById(id).get();
        if(username.equals(aux.getUsername())){
            return ResponseEntity.status(HttpStatus.OK).body(aux);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permissions");
    }
}