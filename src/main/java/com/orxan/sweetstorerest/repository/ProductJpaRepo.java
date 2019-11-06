package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepo extends JpaRepository<Product,Integer> {

    @Query("Select new com.orxan.sweetstorerest.dtos.ProductDTO(p.id, p.name, p.quantity, p.updateDate, p.price, p.isActive)" +
            " FROM Product p WHERE p.isActive=true")
    List<ProductDTO> findByIsActiveTrue();

    Product findFirstByName (String name);

    @Query("SELECT new com.orxan.sweetstorerest.dtos.ProductDTO(p.id, p.name, p.quantity, p.updateDate, p.price, p.isActive)" +
            " FROM Product p WHERE p.quantity>:quantity")
    List<ProductDTO> findAllProductsByQuantity(@Param("quantity") int quantity);

    int countByIsActiveTrue();
}
