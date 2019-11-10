package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.ProductDTO;
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
                                                         @RequestParam int rowsPerPage) {
        ProductsDTO productsDTO =productService.getProductList(startPage,rowsPerPage);
        ResponseObject<ProductsDTO> responseObject=new ResponseObject<>("success",productsDTO);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<ResponseObject> getProductListInStock() {
        List<ProductDTO> list=productService.getProductListInStock();
        ResponseObject<List<ProductDTO>> responseObject=new ResponseObject<>("success",list);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable int id) {
       ProductDTO product=productService.getProductById(id);
       ResponseObject<ProductDTO> responseObject= new ResponseObject<>("success", product);
       return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/count")

    public ResponseEntity<ResponseObject> getTotalCount(){
       int count= productService.getTotalCountOfProduct();
       ResponseObject<Integer> responseObject=new ResponseObject<>("success",count);
       return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
       ProductDTO productAdded=productService.addProduct(product);
       ResponseObject<ProductDTO> responseObject=new ResponseObject<>("success",productAdded);
       return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        productService.deleteProductByID(id);
        String message="Product deleted successfully.Id="+id;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody Product product, @PathVariable int id) {
        ProductDTO newProduct=productService.updateProduct(product,id);
        ResponseObject<ProductDTO> responseObject=new ResponseObject<>("success",newProduct);
        return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }


}
