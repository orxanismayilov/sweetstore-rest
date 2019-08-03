package com.orxan.sweetstorerest.service;


import com.orxan.sweetstorerest.enums.UserRole;
import com.orxan.sweetstorerest.model.User;

public interface UserService {

    User validateLogin(User user);

    UserRole getUserRole(String username);
}
