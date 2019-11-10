package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.UserDTO;
import com.orxan.sweetstorerest.model.MyUserPrincipal;
import com.orxan.sweetstorerest.model.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/login")
public class UserController {

    private final MyUserPrincipal userPrincipal;

    private final ModelMapper mapper;

    @Autowired
    public UserController(MyUserPrincipal userPrincipal, ModelMapper mapper) {
        this.userPrincipal = userPrincipal;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity login() {
        UserDTO userDTO=mapper.map(userPrincipal.getUser(),UserDTO.class);
        ResponseObject<UserDTO> responseObject=new ResponseObject<>("success",userDTO);
        return new ResponseEntity(responseObject,HttpStatus.OK);
    }
}
