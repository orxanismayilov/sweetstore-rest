package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders(@RequestParam int startPage,
                                    @RequestParam int rowsPerPage) {
       return orderService.getOrderList(startPage,rowsPerPage);
    }

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable  int id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/orders")
    public  void addOrder(@RequestBody Order order) {
        orderService.addNewOrderToList(order);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrderByTransactionId(id);
    }

    @PutMapping("/orders/{id}")
    public  void updateOrder(@RequestBody Order order,@PathVariable int id) {
        orderService.updateOrderById(order,id);
    }
}
