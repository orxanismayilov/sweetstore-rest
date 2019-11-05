package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.OrderProductDTO;
import com.orxan.sweetstorerest.dtos.OrderProductsDTO;
import com.orxan.sweetstorerest.model.OrderProduct;

import java.math.BigDecimal;
import java.util.List;

public interface OrderProductService {

    OrderProduct saveOrderProduct(OrderProduct orderProduct,String username);

    boolean removeOrderProductById(int id,String username);

    OrderProductDTO getOrderProduct(int id, String username);

    OrderProductsDTO getOrderProductByOrderId(int orderId,String username);

    OrderProduct updateOrderProduct(OrderProduct newOrderProduct, int id,String username);

    List<String> validateOrderProduct(OrderProduct orderProduct,String username);
}
