package com.orxan.sweetstorerest.repository;


import com.orxan.sweetstorerest.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepo
        extends CrudRepository<User, Integer> {
    Optional<User> findFirstByNameAndIsActiveTrue(String username);


}
