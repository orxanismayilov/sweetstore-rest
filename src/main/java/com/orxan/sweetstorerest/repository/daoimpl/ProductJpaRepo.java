package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductJpaRepo extends CrudRepository<Product,Integer> {


    Product findFirstByName (String name);

}
