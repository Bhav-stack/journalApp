package com.example.journalApp.controller;

import com.example.journalApp.Service.UserService;
import com.example.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthcheck(){
        return "ok";
    }


    @PostMapping("create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);

    }
}
