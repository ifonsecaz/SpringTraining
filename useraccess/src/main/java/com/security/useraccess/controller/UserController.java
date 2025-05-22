package com.security.useraccess.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.security.useraccess.repository.UserRepository;

import jakarta.websocket.server.PathParam;

import org.springframework.stereotype.Controller;

import org.springframework.http.ResponseEntity;
import com.security.useraccess.entity.User;

@Controller
@RequestMapping("/users/delete")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public String register(Model model) {
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public String deleteUser(@RequestParam(name ="user_id") String user_id, Model model) {
        System.out.println(user_id);
        System.out.println("entre");
        Long id=Long.parseLong(user_id);
        if (!userRepository.existsById(id)) {
            return "Not found";
        }
        User aux=userRepository.findById(id).get();
        aux.removeRole();
        userRepository.delete(aux);
        return "/";
    }
}