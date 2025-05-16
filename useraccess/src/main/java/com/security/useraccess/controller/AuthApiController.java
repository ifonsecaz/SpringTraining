package com.security.useraccess.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.useraccess.entity.*;
import com.security.useraccess.repository.UserRepository;
import com.security.useraccess.security.EmailService;
import com.security.useraccess.security.JwtUtil;
import com.security.useraccess.security.RateLimiterService;
import com.security.useraccess.security.RoleService;

import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private RateLimiterService rateLimiterService;
    @Autowired
    private EmailService emailService;

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody SimpleUser ruser, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"login");
        System.out.println("try");
        if (bucket.tryConsume(1)) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                ruser.getUsername(),
                                ruser.getPassword()
                        )
                );
            } catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            User user = userRepository.findByUsername(ruser.getUsername());

            String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit
            user.setMfaOtp(otp);
            user.setMfaOtpExpiry(LocalDateTime.now().plusMinutes(5));

            // Send OTP via email
            emailService.sendOtp(user.getEmail(), otp);

            return ResponseEntity.status(HttpStatus.OK).body("OTP code sended to your email");
            //String token = jwtUtils.generateToken(user.getUsername());
            //return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many attempts");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null || user.getMfaOTP() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No OTP found");
        }

        if (!user.getMfaOTP().equals(request.getMfaOtp())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        if (user.getMfaOtpExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP expired");
        }

        // OTP is valid. Clear OTP and issue token
        user.setMfaOtp(null);
        user.setMfaOtpExpiry(null);
        userRepository.save(user);

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
