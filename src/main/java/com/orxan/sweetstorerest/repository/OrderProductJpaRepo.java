package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.OrderProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.orxan.sweetstorerest.dtos.OrderProductDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductJpaRepo extends CrudRepository<OrderProduct,Integer> {

    @Query("SELECT new com.orxan.sweetstorerest.dtos.OrderProductDTO(op.id, op.orderId, op.productId,p.name,op.productQuantity,op.productPrice,op.totalPrice,op.discount,op.description,op.isActive)" +
            "FROM OrderProduct op INNER JOIN op.product p on op.productId=p.id WHERE op.orderId=:orderId and op.isActive=true ")
    List<OrderProductDTO> findByOrderId(@Param("orderId") int orderId);

     Optional<OrderProduct> findByIdAndIsActiveTrue(int id);
}
