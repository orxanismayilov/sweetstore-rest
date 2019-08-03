package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.model.OrderProduct;

import java.math.BigDecimal;
import java.util.List;

public interface OrderProductService {

    OrderProduct saveOrderProduct(OrderProduct orderProduct);

    boolean removeOrderProductById(int id);

    OrderProduct getOrderProduct(int id);

    List<OrderProduct> getOrderProductByOrderId(int orderId);

    OrderProduct updateOrderProduct(OrderProduct newOrderProduct, int id);

    List<String> validateOrderProduct(OrderProduct orderProduct);

    BigDecimal getTotalDiscount(int orderId);
}
