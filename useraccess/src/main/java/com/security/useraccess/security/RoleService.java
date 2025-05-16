package com.security.useraccess.security;

import com.security.useraccess.entity.Role;
import com.security.useraccess.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
    public Role verifyRole(String role){
        Role res=roleRepository.findByRole(role);
        if(res!=null){
            return res;
        }
        return null;
    }
}
