package com.security.useraccess.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.security.useraccess.repository.UserRepository;
import com.security.useraccess.security.RateLimiterService;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.security.useraccess.entity.User;
import com.security.useraccess.entity.PasswordResetRequest;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    private final UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RateLimiterService rateLimiterService;

   

    public ApiUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    @PostMapping("/resetpwd")
    public ResponseEntity<?> getUserInfo(@RequestBody PasswordResetRequest req, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"resetpwd");

        if (bucket.tryConsume(1)) {
            if (!req.new_password.equals(req.confirm_password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't match");
            }
            //Here add pwd strength validation, same for register
            /*
            * if (req.new_password.length() < 8) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password too short");
                }
            */
            Authentication authenticationName = SecurityContextHolder.getContext().getAuthentication();
            String username = authenticationName.getName();

            try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, req.password)
                );
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is incorrect");
            }

            User user=userRepository.findByUsername(username);
            user.setPassword(encoder.encode(req.new_password));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Password was updated");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many attempts");
        }
        
    }
        
}