package com.orxan.sweetstorerest.repository.daoimpl;



import com.orxan.sweetstorerest.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepo
      extends CrudRepository<User,Integer>
{
    User findFirstByName(String username);


}
