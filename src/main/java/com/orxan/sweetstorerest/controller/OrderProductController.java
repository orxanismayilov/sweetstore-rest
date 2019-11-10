package com.orxan.sweetstorerest.controller;

import com.orxan.sweetstorerest.dtos.OrderProductDTO;
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

    private final OrderProductService orderProductService;

    @Autowired
    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @GetMapping("/list/{orderId}")
    public ResponseEntity<ResponseObject> getOrderProducts(@PathVariable int orderId) {
        OrderProductsDTO dto = orderProductService.getOrderProductByOrderId(orderId);
        ResponseObject<OrderProductsDTO> responseObject = new ResponseObject<>("success", dto);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderProduct(@PathVariable int id) {
        OrderProductDTO orderProduct = orderProductService.getOrderProduct(id);
        ResponseObject<OrderProductDTO> responseObject = new ResponseObject<>("success", orderProduct);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("/list/{orderId}")
    public ResponseEntity<ResponseObject> addOrderProduct(@RequestBody OrderProduct orderProduct,
                                                          @PathVariable int orderId) {
        orderProduct.setOrderId(orderId);
        OrderProduct orderProduct1 = orderProductService.saveOrderProduct(orderProduct);
        ResponseObject<OrderProduct> responseObject = new ResponseObject<>("success", orderProduct1);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @PutMapping("/list/{orderId}/{id}")
    public ResponseEntity<ResponseObject> updateOrderProduct(@RequestBody OrderProduct orderProduct,
                                                             @PathVariable int id) {
        OrderProductDTO orderProduct1 = orderProductService.updateOrderProduct(orderProduct, id);
        ResponseObject<OrderProductDTO> responseObject = new ResponseObject<>("success", orderProduct1);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @DeleteMapping("/list/{orderId}/{id}")
    public ResponseEntity deleteOrderProduct(@PathVariable int id) {
        orderProductService.removeOrderProductById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }
}
