package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.service.serviceimple.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody User user) {
        ResponseObject<Boolean> responseObject=new ResponseObject<>("success",false);

        //if (userService.validateLogin(user)) {
            responseObject.setData(userService.validateLogin(user));
       //    return new ResponseEntity<>(responseObject, HttpStatus.OK);
        //}
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }
}
