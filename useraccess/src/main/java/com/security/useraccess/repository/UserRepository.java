package com.security.useraccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.useraccess.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}