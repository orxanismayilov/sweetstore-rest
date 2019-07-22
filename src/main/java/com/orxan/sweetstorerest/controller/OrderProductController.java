package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orderproducts")
public class OrderProductController {

    @Autowired
    private  OrderProductService orderProductService;

    @GetMapping("/list/{orderId}")
    public ResponseObject getOrderProducts(@PathVariable int orderId) {
        List<OrderProduct>orderProductList=orderProductService.getOrderProductByOrderId(orderId);
        return createResponseObject(orderProductList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseObject getOrderProduct(@PathVariable int id){
        return createResponseObject(orderProductService.getOrderProduct(id),HttpStatus.OK);
    }

    @PostMapping("/list/{orderId}")
    public  ResponseObject addOrderProduct(@RequestBody OrderProduct orderProduct, @PathVariable int orderId){
        orderProduct.setOrderId(orderId);
        OrderProduct orderProduct1=orderProductService.saveOrderProduct(orderProduct);
        return createResponseObject(orderProduct1,HttpStatus.CREATED);
    }
    @PutMapping("/list/{orderId}/{id}")
    public  ResponseObject updateOrderProduct(@RequestBody OrderProduct orderProduct,@PathVariable int id){
        OrderProduct orderProduct1=orderProductService.updateOrderProduct(orderProduct,id);
        return createResponseObject(orderProduct1,HttpStatus.CREATED);
    }

    @DeleteMapping("/list/{orderId}/{id}")
    public ResponseEntity<String> deleteOrderProduct(@PathVariable int id) {
        orderProductService.removeOrderProductById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }

    private ResponseObject createResponseObject(Object data, HttpStatus status) {
        ResponseObject responseObject=new ResponseObject();
        responseObject.setStatus(status);
        responseObject.setTimestamp(LocalDateTime.now());
        responseObject.setMessage("success");
        responseObject.setData(data);
        return responseObject;
    }

}
