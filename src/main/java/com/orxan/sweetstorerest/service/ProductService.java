package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.dtos.ProductsDTO;
import com.orxan.sweetstorerest.model.Product;

import java.util.List;

public interface ProductService {

    ProductsDTO getProductList(int pageIndex, int rowsPerPage);

    ProductDTO addProduct(Product product);

    ProductDTO updateProduct(Product product, int oldProductId);

    List<String> isProductValid(Product product);

    void deleteProductByID(int id);

    ProductDTO getProductById(int id);

    List<ProductDTO> getProductListInStock();

    int getTotalCountOfProduct();
}
