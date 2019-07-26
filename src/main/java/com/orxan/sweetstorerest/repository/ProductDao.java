package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.Product;

import java.util.List;

public interface ProductDao {


    List<Product> getProductList(int pageIndex, int rowsPerPage);

    List<Product> getProductListForComboBox();

    Product addProduct(Product product);

    void deleteProductById(int id);

    boolean isProductExist(int id);

    Product updateProduct(Product product, int oldProductId);

    Product getProductById(int id);

    int getTotalCountOfProduct();

    Product checkProductNameIsExist(String name);
}
