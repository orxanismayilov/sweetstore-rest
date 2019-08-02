package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderproducts")
public class OrderProductController {

    @Autowired
    private  OrderProductService orderProductService;

    @GetMapping("/list/{orderId}")
    public ResponseEntity<ResponseObject> getOrderProducts(@PathVariable int orderId) {
        List<OrderProduct>orderProductList=orderProductService.getOrderProductByOrderId(orderId);
        ResponseObject<List<OrderProduct>> responseObject=new ResponseObject<>("success",orderProductList);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderProduct(@PathVariable int id){
        OrderProduct orderProduct=orderProductService.getOrderProduct(id);
        ResponseObject<OrderProduct> responseObject=new ResponseObject<>("success",orderProduct);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PostMapping("/list/{orderId}")
    public  ResponseEntity<ResponseObject> addOrderProduct(@RequestBody OrderProduct orderProduct, @PathVariable int orderId){
        orderProduct.setOrderId(orderId);
        OrderProduct orderProduct1=orderProductService.saveOrderProduct(orderProduct);
        ResponseObject<OrderProduct> responseObject=new ResponseObject<>("success",orderProduct1);
        return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }
    @PutMapping("/list/{orderId}/{id}")
    public  ResponseEntity<ResponseObject> updateOrderProduct(@RequestBody OrderProduct orderProduct, @PathVariable int id){
        OrderProduct orderProduct1=orderProductService.updateOrderProduct(orderProduct,id);
        ResponseObject<OrderProduct> responseObject=new ResponseObject<>("success",orderProduct1);
        return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/list/{orderId}/{id}")
    public ResponseEntity deleteOrderProduct(@PathVariable int id) {
        orderProductService.removeOrderProductById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }
}
