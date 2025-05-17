package com.ecommerce.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.userservice.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ecommerce.userservice.entity.User;

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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User aux=userRepository.findById(id).get();
        aux.removeRole();
        userRepository.delete(aux);
        return ResponseEntity.ok("User deleted");
    }

    //delete non verified
    //should be automated
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/non-verified")
    public ResponseEntity<?> deleteNonVerified() {
        List<User> aux=userRepository.findByAcvalidatedFalse();
        for(User user : aux){
            if(user.getOldEmail()!=null){
                user.setEmail(user.getOldEmail());
                user.setAcvalidated(true);
                user.setLastPasswordReset();
                userRepository.save(user);
            }
            else{
                user.removeRole();
                userRepository.delete(user);
            }
        }
        return ResponseEntity.ok("Completed");
    }
}