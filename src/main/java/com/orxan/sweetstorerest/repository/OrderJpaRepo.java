package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepo extends CrudRepository<Order,Integer>{

    int countByIsActiveTrue();

    @Query("Select  new com.orxan.sweetstorerest.dtos.OrderDTO(o.id, o.customerName, o.customerAddress, o.description, o.orderType, o.totalPrice, o.totalDiscount, o.date, o.orderStatus, o.isActive) " +
            "From Order o where o.isActive=true")
    List<OrderDTO> findAllActiveOrders(Pageable pageable);

    Optional<Order> findByIdAndIsActiveTrue(int id);

    @Query("Select  new com.orxan.sweetstorerest.dtos.OrderDTO(o.id, o.customerName, o.customerAddress, o.description, o.orderType, o.totalPrice, o.totalDiscount, o.date, o.orderStatus, o.isActive) " +
            "From Order o where CAST( o.id AS string ) like %:id% and o.isActive=true")
    List<OrderDTO> findByIdLikeAndIsActiveTrue(@Param("id") String id);

    @Query("Select  new com.orxan.sweetstorerest.dtos.OrderDTO(o.id, o.customerName, o.customerAddress, o.description, o.orderType, o.totalPrice, o.totalDiscount, o.date, o.orderStatus, o.isActive) " +
            "From Order o where CAST( o.id AS string ) like %:id%")
    List<OrderDTO> findAllByIdLike(@Param("id") String id);
}
