package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.enums.UserRole;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.repository.daoimpl.UserDaoImpl;
import com.orxan.sweetstorerest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;
    public UserServiceImpl() {
    }

    @Override
    @LoggerAnnotation
    public User validateLogin(User user) {
       User u=userDao.validateLogin(user);
       if (u==null) throw new ResourceNotFoundException("User name or password is wrong.");
       return u;
    }

    @Override
    //@LoggerAnnotation
    public UserRole getUserRole(String username) {
        if (userDao.getUserRole(username)==null) throw new ResourceNotFoundException("user not found");
        return userDao.getUserRole(username);
    }
}
