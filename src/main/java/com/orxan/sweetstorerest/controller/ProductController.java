package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.service.serviceimple.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestParam int startPage,
                                        @RequestParam int rowsPerPage) {
        List<Product> productList=productService.getProductList(startPage,rowsPerPage);
        return productList;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        product.setUpdateDate(LocalDateTime.now());
        productService.addProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProductByID(id);
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@RequestBody Product product,@PathVariable int id) {
        productService.updateProduct(product,id);
    }
}
