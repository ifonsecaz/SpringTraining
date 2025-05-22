package com.logprof.users.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.logprof.users.service.UserService;
import com.logprof.users.entity.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(
        @Valid @RequestBody Users user)
    {
        Users res=userService.saveUser(user);
        if(res!=null){
            logger.info("User was succesfuly saved {}", res.getUser_id());
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            logger.error("The user couldn't be saved, email or username already in use: {}", user);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }


    @GetMapping("/users")
    public List<Users> fetchUserList()
    {
        logger.info("Users list retrieved");
        return userService.fetchUserList();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> fetchUserById(@PathVariable("id") Long userID)
    {
        Users res= userService.fetchUserByID(userID);
        if(res!=null){
            logger.info("User was found: {}", userID);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        logger.warn("We couldn't find the id: {}",userID);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
 
    @PutMapping("/users/{id}")
    public ResponseEntity<?>
    updateUser(@Valid @RequestBody Users user,
                     @PathVariable("id") Long userId)
    {
        Users res= userService.updateUser(user, userId);
        if(res!=null){
            logger.info("User was updated: {}", userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        logger.error("Incorrect ID: {}", userId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
    

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id")
                                       Long userId)
    {
        if(userService.deleteUser(userId)){
            logger.info("User was deleted: {}",userId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted succesfully");
        }
        logger.error("Incorrect ID: {}", userId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

}
