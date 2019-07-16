package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getProductList(int pageIndex, int rowsPerPage);

    Product addProduct(Product product);

    Product updateProduct(Product product, int oldProductId);

    Map<String, Map<Boolean, List<String>>> isProductValid(Product product);

    boolean deleteProductByID(int id);

    Product getProductById(int id);

    List<Product> getProductListForComboBox();

    int getTotalCountOfProduct();
}
