package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductList(int pageIndex, int rowsPerPage);

    Product addProduct(Product product);

    Product updateProduct(Product product, int oldProductId);

    List<String> isProductValid(Product product);

    boolean deleteProductByID(int id);

    Product getProductById(int id);

    List<Product> getProductListForComboBox();

    int getTotalCountOfProduct();

     Product chechkProductNameIsExist(String name);
}
