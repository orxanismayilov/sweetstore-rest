package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.model.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductJpaRepo extends CrudRepository<OrderProduct,Integer> {

    List<OrderProduct> findByOrderId(int orderId);

     Optional<OrderProduct> findByIdAndIsActiveTrue(int id);
}
