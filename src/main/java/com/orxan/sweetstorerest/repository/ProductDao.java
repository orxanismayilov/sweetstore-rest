package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProductList(int pageIndex, int rowsPerPage);

    void addProduct(Product product);

    void deleteProductById(int id);

    Product isProductExist(String name);

    void updateProductIncreaseQuantity(Product newProduct, int id);

    void updateProduct(Product product, int oldProductId);

    Product getProductById(int id);

    int getTotalCountOfProduct();
}
