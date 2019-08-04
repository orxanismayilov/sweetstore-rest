package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.OrderProductsDTO;
import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderproducts")
public class OrderProductController {

    @Autowired
    private  OrderProductService orderProductService;

    @GetMapping("/list/{orderId}")
    public ResponseEntity<ResponseObject> getOrderProducts(@PathVariable int orderId,@RequestParam String username) {
        OrderProductsDTO dto =orderProductService.getOrderProductByOrderId(orderId,username);
        ResponseObject<OrderProductsDTO> responseObject=new ResponseObject<>("success",dto);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderProduct(@PathVariable int id,@RequestParam String username){
        OrderProduct orderProduct=orderProductService.getOrderProduct(id,username);
        ResponseObject<OrderProduct> responseObject=new ResponseObject<>("success",orderProduct);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PostMapping("/list/{orderId}")
    public  ResponseEntity<ResponseObject> addOrderProduct(@RequestBody OrderProduct orderProduct,
                                                           @PathVariable int orderId,
                                                           @RequestParam String username){
        orderProduct.setOrderId(orderId);
        OrderProduct orderProduct1=orderProductService.saveOrderProduct(orderProduct,username);
        ResponseObject<OrderProduct> responseObject=new ResponseObject<>("success",orderProduct1);
        return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }
    @PutMapping("/list/{orderId}/{id}")
    public  ResponseEntity<ResponseObject> updateOrderProduct(@RequestBody OrderProduct orderProduct,
                                                              @PathVariable int id,
                                                              @RequestParam String username){
        OrderProduct orderProduct1=orderProductService.updateOrderProduct(orderProduct,id,username);
        ResponseObject<OrderProduct> responseObject=new ResponseObject<>("success",orderProduct1);
        return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/list/{orderId}/{id}")
    public ResponseEntity deleteOrderProduct(@PathVariable int id,@RequestParam String username) {
        orderProductService.removeOrderProductById(id,username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }
}
