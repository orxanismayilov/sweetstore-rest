package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getProductList(int pageIndex, int rowsPerPage);

    Map addProduct(Product product);

    Map updateProduct(Product product, int oldProductId);

    Map<String, Map<Boolean, List<String>>> isProductValid(Product product);

    boolean deleteProductByID(int id);

    Product getProductById(int id);

    int getTotalCountOfProduct();
}
