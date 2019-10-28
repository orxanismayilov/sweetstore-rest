package com.orxan.sweetstorerest.repository;


import com.orxan.sweetstorerest.enums.UserRole;
import com.orxan.sweetstorerest.model.User;

public interface UserDao {
    void startUserSesion(User user);

    void addUserAddList(User user);

    void deleteUserById(int id);

    User validateLogin(User user);

    String getUserRole(String username);
}
