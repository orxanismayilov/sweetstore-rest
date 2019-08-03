package com.orxan.sweetstorerest.service.serviceimple;

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

    @Override
    public User validateLogin(User user) {
       User u=userDao.validateLogin(user);
       if (u==null) throw new ResourceNotFoundException("User name or password is wrong.");
       return u;
    }
}
