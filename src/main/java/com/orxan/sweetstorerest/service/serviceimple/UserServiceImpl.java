package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;

import com.orxan.sweetstorerest.dtos.UserDTO;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.repository.daoimpl.UserDaoImpl;
import com.orxan.sweetstorerest.repository.UserJpaRepo;
import com.orxan.sweetstorerest.service.UserService;
import com.orxan.sweetstorerest.util.PasswordAuthentication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private UserJpaRepo jparepo;

    public UserServiceImpl() {
    }

    @Override
    @LoggerAnnotation
    public UserDTO validateLogin(User user) {
       User entity= jparepo.findFirstByName(user.getName());
        PasswordAuthentication encoder=new PasswordAuthentication();
       if (!encoder.authenticate(user.getPassword(),entity.getPassword())) throw new ResourceNotFoundException("User name or password is wrong.");
       ModelMapper mapper=new ModelMapper();
       UserDTO userDto=mapper.map(entity,UserDTO.class);
       return userDto;
    }

    @Override
    @LoggerAnnotation
    public String getUserRole(String username) {
        if (userDao.getUserRole(username)==null) throw new ResourceNotFoundException("user not found");
        return userDao.getUserRole(username);
    }
}
