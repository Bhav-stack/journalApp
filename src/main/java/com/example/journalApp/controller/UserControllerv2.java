package com.example.journalApp.controller;


import com.example.journalApp.Service.UserService;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControllerv2 {

    @Autowired
    private UserService userService;
@Autowired
private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

@PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
        User userInDb=userService.findByUsername(username);

            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@DeleteMapping("/user")
    public ResponseEntity<?> deleteById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}