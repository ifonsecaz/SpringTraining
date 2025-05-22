package com.security.useraccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.useraccess.repository.*;
import com.security.useraccess.security.RoleService;

import jakarta.transaction.Transactional;

import com.security.useraccess.entity.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String registerUser(User user) {
        if(user.getUsername()!=null && user.getPassword()!=null && user.getEmail()!=null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role aux= roleService.verifyRole("USER");
            System.out.println(aux.getRole_id());
            if(aux!=null&&userRepository.findByUsername(user.getUsername())==null){
                userRepository.save(user);
                user.setRole(aux);
                logger.info("User was succesfuly saved {}", user.getUser_id());            
                return "redirect:/login";
            }
        }
        logger.error("Invalid input");
        return "redirect:/register";
    }
}
