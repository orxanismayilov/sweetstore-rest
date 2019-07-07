package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.service.serviceimple.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/users")
    public String login(@RequestBody User user) {
        if (userService.validateLogin(user)) {
            return "Welcome "+user.getName();
        }
        return "Wrong pass or username";
    }
}
