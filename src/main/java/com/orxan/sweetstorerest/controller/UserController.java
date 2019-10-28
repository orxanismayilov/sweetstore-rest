package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.UserDTO;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.service.serviceimple.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity login(@RequestBody User user) {
        ResponseObject<UserDTO> responseObject=new ResponseObject<>("",userService.validateLogin(user));
        return new ResponseEntity(responseObject,HttpStatus.OK);
    }
}
