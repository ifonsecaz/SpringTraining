package com.logprof.users.service;

import java.util.List;
import java.util.Optional;

import com.logprof.users.entity.*;
import com.logprof.users.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    //Save
    public Users saveUser(Users user){
        return userRepository.save(user);
    }

    //Read  
    public List<Users> fetchUserList(){
        return userRepository.findAll();
    }

    //Read 1
    public Users fetchUserByID(Long userID){
        Optional<Users> res= userRepository.findById(userID);
        if(res.isPresent()){
            return res.get();
        }
        return null;
    }

    //Delete
    public boolean deleteUser(Long userID){
        boolean res=false;
        if(userRepository.findById(userID).isPresent()){
            res=true;
            userRepository.deleteById(userID);
        }
        return res;
    }

    //Update
    public Users updateUser(Users user, Long userID){
        if(userRepository.findById(userID).isPresent()){
            Users aux=userRepository.findById(userID).get();

            if(!aux.getUsername().equals(user.getUsername()))
                aux.setUsername(user.getUsername());
            if(!aux.getEmail().equals(user.getEmail()))
                aux.setEmail(user.getEmail());
            if(aux.getBirth_date().compareTo(user.getBirth_date())!=0)
                aux.setBirth_date(user.getBirth_date());
            return userRepository.save(aux);
        }
        return null;
    }
}
