package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.service.serviceimple.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String login(Principal user) {
        return user != null ? "Y" : "N";
    }
}
