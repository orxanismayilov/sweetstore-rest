package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.model.ResponseObject;
import com.orxan.sweetstorerest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllOrders(@RequestParam int pageIndex, @RequestParam int maxRows) {
       List<Order> orders= orderService.getOrderList(pageIndex,maxRows);
       ResponseObject<List<Order>> responseObject=new ResponseObject<>("success",orders);
       return createResponseObject(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrder(@PathVariable  int id) {
        Order order= orderService.getOrder(id);
        ResponseObject<Order> responseObject=new ResponseObject<>("success",order);
        return createResponseObject(responseObject,HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<ResponseObject> addOrder(@RequestBody Order order) {
       Order order1= orderService.addNewOrderToList(order);
       ResponseObject<Order> responseObject=new ResponseObject<>("success",order1);
       return createResponseObject(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable int id) {
        orderService.deleteOrderByTransactionId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateOrder(@RequestBody Order order, @PathVariable int id) {
       Order order1= orderService.updateOrderById(order,id);
       ResponseObject<Order> responseObject=new ResponseObject<>("success",order1);
       return createResponseObject(responseObject,HttpStatus.CREATED);
    }

    private ResponseEntity<ResponseObject> createResponseObject(ResponseObject data, HttpStatus status) {
        return new ResponseEntity<>(data,status);
    }
}
