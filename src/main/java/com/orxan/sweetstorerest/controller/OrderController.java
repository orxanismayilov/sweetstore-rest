package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.service.OrderService;
import com.orxan.sweetstorerest.util.OrderResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderResourceAssembler orderResourceAssembler;

    @GetMapping("/orders")
    public List<Order> getAllOrders(@RequestParam int pageIndex, @RequestParam int maxRows) {
       return orderService.getOrderList(pageIndex,maxRows);
    }

    @GetMapping("/orders/{id}")
    public Resource<Order> getOrder(@PathVariable  int id) {
        Order order= orderService.getOrder(id);
        return orderResourceAssembler.toResource(order);
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
