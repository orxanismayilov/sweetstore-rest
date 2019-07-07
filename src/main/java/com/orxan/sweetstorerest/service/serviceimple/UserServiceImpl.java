package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.repository.UserDao;
import com.orxan.sweetstorerest.repository.daoimpl.UserDaoImpl;
import com.orxan.sweetstorerest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public boolean validateLogin(User user) {
       return userDao.validateLogin(user);
    }
}
