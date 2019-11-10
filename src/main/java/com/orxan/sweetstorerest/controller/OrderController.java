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
    public ResponseEntity<ResponseObject> getAllOrders(@RequestParam int pageIndex, @RequestParam int maxRows) {
       OrdersDTO orders= orderService.getOrderList(pageIndex,maxRows);
       ResponseObject<OrdersDTO> responseObject=new ResponseObject<>("success",orders);
       return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrder(@PathVariable  int id) {
        OrderDTO order= orderService.getOrder(id);
        ResponseObject<OrderDTO> responseObject=new ResponseObject<>("success",order);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<ResponseObject> findOrderById(@RequestParam String id,
                                                        @RequestParam(value="getAll",defaultValue = "false")
                                                                boolean getAll) {
        List<OrderDTO> list=orderService.searchOrderById(id,getAll);
        ResponseObject<List<OrderDTO>> responseObject=new ResponseObject<>("success",list);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseObject> getTotalCountofOrder() {
        int count=orderService.getTotalCountOfOrder();
        ResponseObject responseObject=new ResponseObject<>("success",count);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<ResponseObject> addOrder(@RequestBody Order order) {
       OrderDTO orderDTO= orderService.addOrder(order);
       ResponseObject<OrderDTO> responseObject=new ResponseObject<>("success",orderDTO);
       return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable int id) {
        orderService.deleteOrderByTransactionId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateOrder(@RequestBody Order order, @PathVariable int id) {
       OrderDTO orderDTO= orderService.updateOrderById(order,id);
       ResponseObject<OrderDTO> responseObject=new ResponseObject<>("success",orderDTO);
       return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
    }
}
