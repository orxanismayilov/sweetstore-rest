package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public interface ProductJpaRepo extends CrudRepository<Product,Integer> {

    Iterable<Product> findByIsActiveTrue();

    Product findFirstByName (String name);

    Iterable<Product> findByQuantityGreaterThanAndIsActiveTrue(int i);
}
