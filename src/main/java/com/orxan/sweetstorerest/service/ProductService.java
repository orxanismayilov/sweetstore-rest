package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.dtos.ProductsDTO;
import com.orxan.sweetstorerest.model.Product;

import java.util.List;

public interface ProductService {

    ProductsDTO getProductList(int pageIndex, int rowsPerPage,String username);

    ProductDTO addProduct(Product product, String username);

    ProductDTO updateProduct(Product product, int oldProductId,String username);

    List<String> isProductValid(Product product);

    boolean deleteProductByID(int id,String username);

    ProductDTO getProductById(int id,String username);

    List<ProductDTO> getProductListInStock(String username);

    int getTotalCountOfProduct(String username);
}
