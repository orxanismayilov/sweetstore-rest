package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.UserDTO;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.repository.UserJpaRepo;
import com.orxan.sweetstorerest.service.UserService;
import com.orxan.sweetstorerest.util.PasswordAuthentication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepo jpaRepo;

    public UserServiceImpl() {
    }

    @Override
    @LoggerAnnotation
    public UserDTO validateLogin(User user) {
       User entity= jpaRepo.findFirstByNameAndIsActiveTrue(user.getName()).orElseThrow(()->new ResourceNotFoundException("User not found."));
        PasswordAuthentication encoder=new PasswordAuthentication();
       if (!encoder.authenticate(user.getPassword(),entity.getPassword())) throw new ResourceNotFoundException("User name or password is wrong.");
       ModelMapper mapper=new ModelMapper();
        return mapper.map(entity,UserDTO.class);
    }

    @Override
    @LoggerAnnotation
    public String getUserRole(String username) {
        return "ADMIN";
    }
}
