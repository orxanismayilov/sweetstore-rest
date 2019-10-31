package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepo extends CrudRepository<Order,Integer>{

    int countByIsActiveTrue();

    List<Order> findByIsActiveTrue();

    Optional<Order> findByIdAndIsActiveTrue(int id);
}
