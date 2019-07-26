package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.serviceimple.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllProducts(@RequestParam int startPage,
                                                         @RequestParam int rowsPerPage) {
        List<Product> productList=productService.getProductList(startPage,rowsPerPage);
        ResponseObject<List<Product>> responseObject=new ResponseObject<>("success",productList);
        return createResponseObject(responseObject,HttpStatus.OK);
    }

    @GetMapping("/combo-box")
    public ResponseEntity<ResponseObject> getProductListForComboBox() {
        List<Product> list=productService.getProductListForComboBox();
        ResponseObject<List<Product>> responseObject=new ResponseObject<>("success",list);
        return createResponseObject(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable int id) {
       Product product=productService.getProductById(id);
       ResponseObject<Product> responseObject= new ResponseObject<>("success", product);
       return createResponseObject(responseObject,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
       Product productAdded=productService.addProduct(product);
       ResponseObject<Product> responseObject=new ResponseObject<>("success",productAdded);
       return createResponseObject(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        productService.deleteProductByID(id);
        String message="Product deleted successfully.Id="+id;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody Product product,@PathVariable int id) {
        Product newProduct=productService.updateProduct(product,id);
        ResponseObject<Product> responseObject=new ResponseObject<>("success",newProduct);
        return createResponseObject(responseObject,HttpStatus.CREATED);
    }

    private ResponseEntity<ResponseObject> createResponseObject(ResponseObject data, HttpStatus status) {
        return new ResponseEntity<>(data,status);
    }

}
