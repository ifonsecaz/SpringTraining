package com.security.useraccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.useraccess.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}