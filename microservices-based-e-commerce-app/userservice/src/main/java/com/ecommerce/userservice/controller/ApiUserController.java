package com.ecommerce.userservice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.security.RateLimiterService;
import com.ecommerce.userservice.service.EmailService;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.entity.EmailChangeRequest;
import com.ecommerce.userservice.entity.PasswordResetRequest;
import com.ecommerce.userservice.entity.ProductDTO;

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
    @Autowired
    private EmailService emailService;


    public ApiUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userRepository.findByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/resetpwd")
    public ResponseEntity<?> getUserInfo(@RequestBody PasswordResetRequest req, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"resetpwd");

        if (bucket.tryConsume(1)) {
            if (!req.new_password.equals(req.confirm_password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't match");
            }

            if (req.new_password.length() < 8) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password too short");
            }

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
        
    //Need logic to not make the mail change if account is not validated
    @PostMapping("/changemail")
    public ResponseEntity<?> getUserInfo(@RequestBody EmailChangeRequest req, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"resetpwd");

        if (bucket.tryConsume(1)) {
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
            user.setOldEmail(user.getEmail());
            user.setEmail(req.email);
            user.setAcvalidated(false);
            user.setLastPasswordReset(); //Used to check when last change was made
            userRepository.save(user); 

            String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit
            user.setMfaOtp(otp);
            user.setMfaOtpExpiry(LocalDateTime.now().plusMinutes(5));

            // Send OTP via email
            emailService.sendOtp(user.getEmail(), otp);

            return ResponseEntity.status(HttpStatus.OK).body("Email was updated, please verify your mail");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many attempts");
        }
        
    }

    //Need logic change
    @PutMapping("/products/product/{id}/unitsbuy/{units}")
    public ResponseEntity<?>
    buyProduct(@PathVariable("id") Long productId,
                        @PathVariable("units") int units)
    {
        final String uri = "http://localhost:8081/products/update/product/"+productId+"/unitsbuy/"+units;
        RestTemplate restTemplate = new RestTemplate();
        try{
            return restTemplate.exchange(uri, HttpMethod.PUT, null, ProductDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

}