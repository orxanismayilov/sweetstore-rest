package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.serviceimple.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    public ResponseObject getAllProducts(@RequestParam int startPage,
                                        @RequestParam int rowsPerPage) {
        List<Product> productList=productService.getProductList(startPage,rowsPerPage);
        return createResponseObject("Products",productList,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseObject getProduct(@PathVariable int id) {
       Product product=productService.getProductById(id);
       return createResponseObject("Product id="+id,product,HttpStatus.OK);
    }

    @PostMapping
    public ResponseObject addProduct(@RequestBody Product product) {
       Product productAdded=productService.addProduct(product);
       return createResponseObject("Product created.",productAdded,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProductByID(id);
        String message="Product deleted successfully.Id="+id;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @PutMapping("/{id}")
    public ResponseObject updateProduct(@RequestBody Product product,@PathVariable int id) {
        Product newProduct=productService.updateProduct(product,id);
        return createResponseObject("Product updated.",newProduct,HttpStatus.CREATED);
    }

    private ResponseObject createResponseObject(String msg, Object data, HttpStatus status) {
        ResponseObject responseObject=new ResponseObject();
        responseObject.setStatus(status);
        responseObject.setTimestamp(LocalDateTime.now());
        responseObject.setMessage(msg);
        responseObject.setData(data);
        return responseObject;
    }

}
