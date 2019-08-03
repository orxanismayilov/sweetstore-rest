package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.ProductsDTO;
import com.orxan.sweetstorerest.model.Product;

import java.util.List;

public interface ProductService {

    ProductsDTO getProductList(int pageIndex, int rowsPerPage,String username);

    Product addProduct(Product product,String username);

    Product updateProduct(Product product, int oldProductId,String username);

    List<String> isProductValid(Product product);

    boolean deleteProductByID(int id,String username);

    Product getProductById(int id,String username);

    List<Product> getProductListInStock(String username);

    int getTotalCountOfProduct(String username);
}
