package com.orxan.sweetstorerest.service;


import com.orxan.sweetstorerest.dtos.UserDTO;
import com.orxan.sweetstorerest.model.User;

public interface UserService {

    UserDTO validateLogin(User user);

    String getUserRole(String username);
}
