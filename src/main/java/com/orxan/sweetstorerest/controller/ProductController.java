package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.ProductsDTO;
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
                                                         @RequestParam int rowsPerPage,
                                                         @RequestParam String username) {
        ProductsDTO productsDTO =productService.getProductList(startPage,rowsPerPage,username);
        ResponseObject<ProductsDTO> responseObject=new ResponseObject<>("success",productsDTO);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<ResponseObject> getProductListInStock(@RequestParam String username) {
        List<Product> list=productService.getProductListInStock(username);
        ResponseObject<List<Product>> responseObject=new ResponseObject<>("success",list);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable int id,@RequestParam String username) {
       Product product=productService.getProductById(id,username);
       ResponseObject<Product> responseObject= new ResponseObject<>("success", product);
       return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/count")

    public ResponseEntity<ResponseObject> getTotalCount(@RequestParam String username){
       int count= productService.getTotalCountOfProduct(username);
       ResponseObject<Integer> responseObject=new ResponseObject<>("success",count);
       return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product,@RequestParam String username) {
       Product productAdded=productService.addProduct(product,username);
       ResponseObject<Product> responseObject=new ResponseObject<>("success",productAdded);
       return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id,@RequestParam String username) {
        productService.deleteProductByID(id,username);
        String message="Product deleted successfully.Id="+id;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody Product product,@PathVariable int id,@RequestParam String username) {
        Product newProduct=productService.updateProduct(product,id,username);
        ResponseObject<Product> responseObject=new ResponseObject<>("success",newProduct);
        return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }


}
