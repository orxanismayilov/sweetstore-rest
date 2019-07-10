package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.util.OrderProductAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderProductController {

    @Autowired
    private final OrderProductService orderProductService;
    @Autowired
    private final OrderProductAssembler assembler;

    public OrderProductController(OrderProductService orderProductService, OrderProductAssembler assembler) {
        this.orderProductService = orderProductService;
        this.assembler = assembler;
    }


    @GetMapping("/orderproducts")
    public List<OrderProduct> getOrderProducts(@PathVariable int orderId) {
        return orderProductService.getOrderProductByOrderId(orderId);
    }

    @GetMapping("/orderproducts/{id}")
    public Resource<OrderProduct> getOrderProduct(@PathVariable int id){
        return assembler.toResource(orderProductService.getOrderProduct(id));
    }

    @PostMapping("/orderproducts")
    public  void addOrderProduct(@RequestBody OrderProduct orderProduct){
        Map<String,Map<Boolean,List<String>>> validation=orderProductService.validateOrderProduct(orderProduct);
        if (!validation.get("quantityError").containsKey(true) && !validation.get("discountError").containsKey(true) && !validation.get("totalPriceError").containsKey(true)) {
            orderProductService.saveOrderProduct(orderProduct);
        }
    }
    @PutMapping("/orderproducts/{id}")
    public  void updateOrderProduct(@RequestBody OrderProduct orderProduct,@PathVariable int id){
        Map<String,Map<Boolean,List<String>>> validation=orderProductService.validateOrderProduct(orderProduct);
        if (!validation.get("quantityError").containsKey(true) && !validation.get("discountError").containsKey(true) && !validation.get("totalPriceError").containsKey(true)) {
            orderProductService.updateOrderProduct(orderProduct,id);
        }
    }

    @DeleteMapping("/orderproducts/{id}")
    public  void deleteOrderProduct(@PathVariable int id) {
        orderProductService.removeOrderProductById(id);
    }

}
