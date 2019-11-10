package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.model.MyUserPrincipal;
import com.orxan.sweetstorerest.repository.UserJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserJpaRepo repo;

    @Autowired
    private MyUserPrincipal userPrincipal;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        userPrincipal.setUser(repo.findFirstByNameAndIsActiveTrue(s).orElseThrow(()->new UsernameNotFoundException("User not found.")));
        return userPrincipal;
    }
}
