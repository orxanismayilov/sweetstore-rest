package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.OrderProductDTO;
import com.orxan.sweetstorerest.dtos.OrderProductsDTO;
import com.orxan.sweetstorerest.model.OrderProduct;

import java.util.List;

public interface OrderProductService {

    OrderProduct saveOrderProduct(OrderProduct orderProduct);

    boolean removeOrderProductById(int id);

    OrderProductDTO getOrderProduct(int id);

    OrderProductsDTO getOrderProductByOrderId(int orderId);

    OrderProductDTO updateOrderProduct(OrderProduct newOrderProduct, int id);

    List<String> validateOrderProduct(OrderProduct orderProduct);
}
