package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
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
    public ResponseEntity<ResponseObject> getAllOrders(@RequestParam int pageIndex, @RequestParam int maxRows,@RequestParam String username) {
       OrdersDTO orders= orderService.getOrderList(pageIndex,maxRows,username);
       ResponseObject<OrdersDTO> responseObject=new ResponseObject<>("success",orders);
       return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrder(@PathVariable  int id,@RequestParam String username) {
        OrderDTO order= orderService.getOrder(id,username);
        ResponseObject<OrderDTO> responseObject=new ResponseObject<>("success",order);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<ResponseObject> findOrderById(@RequestParam int id,
                                                        @RequestParam(value="getAll",defaultValue = "false")
                                                                boolean getAll,
                                                        @RequestParam String username) {
        List<Order> list=orderService.searchOrderById(id,getAll,username);
        ResponseObject<List<Order>> responseObject=new ResponseObject<>("success",list);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseObject> getTotalCountofOrder(@RequestParam String username) {
        int count=orderService.getTotalCountOfOrder(username);
        ResponseObject responseObject=new ResponseObject<>("success",count);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<ResponseObject> addOrder(@RequestBody Order order,@RequestParam String username) {
       Order order1= orderService.addOrder(order,username);
       ResponseObject<Order> responseObject=new ResponseObject<>("success",order1);
       return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable int id,@RequestParam String username) {
        orderService.deleteOrderByTransactionId(id,username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateOrder(@RequestBody Order order, @PathVariable int id,@RequestParam String username) {
       Order order1= orderService.updateOrderById(order,id,username);
       ResponseObject<Order> responseObject=new ResponseObject<>("success",order1);
       return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }
}
