package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.serviceimple.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/products")
    public ResponseObject getAllProducts(@RequestParam int startPage,
                                        @RequestParam int rowsPerPage) {
        List<Product> productList=productService.getProductList(startPage,rowsPerPage);
        ResponseObject responseObject=new ResponseObject();
        if (!productList.isEmpty()) {
            responseObject.setStatus(HttpStatus.OK);
            responseObject.setTimestamp(LocalDateTime.now());
            responseObject.setMessage("Products");
            responseObject.setData(productList);
        } else {
            throw new ResourceNotFoundException("No products found!");
        }
        return responseObject;
    }


    @GetMapping("/products/{id}")
    public ResponseObject getProduct(@PathVariable int id) {
       ResponseObject responseObject=new ResponseObject();
       Product product=productService.getProductById(id);
       if (product!=null) {
           responseObject.setStatus(HttpStatus.OK);
           responseObject.setMessage("Product id="+id);
           responseObject.setData(product);
           responseObject.setTimestamp(LocalDateTime.now());
       } else throw new ResourceNotFoundException("Product Not found id="+id);
       return responseObject;
    }

    @PostMapping("/products")
    public ResponseObject addProduct(@RequestBody Product product) {
       Product productAdded=productService.addProduct(product);
       ResponseObject responseObject=new ResponseObject();
       responseObject.setStatus(HttpStatus.CREATED);
       responseObject.setTimestamp(LocalDateTime.now());
       responseObject.setMessage("Product created.");
       responseObject.setData(productAdded);
       return responseObject;
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
