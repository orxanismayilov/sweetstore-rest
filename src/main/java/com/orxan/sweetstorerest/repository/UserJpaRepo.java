package com.orxan.sweetstorerest.repository;



import com.orxan.sweetstorerest.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepo
      extends CrudRepository<User,Integer>
{
    User findFirstByName(String username);


}
