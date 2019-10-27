package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.entity.UserEntity;
import com.orxan.sweetstorerest.repository.UserDao;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepo
      extends CrudRepository<UserEntity,Integer>
{
    UserEntity findFirstByName(String username);


}
