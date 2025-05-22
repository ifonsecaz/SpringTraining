package com.logprof.users.service;

import java.util.List;

import com.logprof.users.entity.Users;;

public interface UserService {
    //Save
    public Users saveUser(Users user);

    //Read  
    public List<Users> fetchUserList();

    //Read 1
    public Users fetchUserByID(Long userID);

    //Delete
    public boolean deleteUser(Long userID);

    //Update
    public Users updateUser(Users user, Long userID);
}