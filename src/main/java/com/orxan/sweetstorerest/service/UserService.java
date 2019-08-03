package com.orxan.sweetstorerest.service;


import com.orxan.sweetstorerest.model.User;

public interface UserService {

    User validateLogin(User user);
}
