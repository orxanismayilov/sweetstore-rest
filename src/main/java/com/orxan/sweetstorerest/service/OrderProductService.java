package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.model.OrderProduct;

import java.util.List;
import java.util.Map;

public interface OrderProductService {

    void saveOrderProduct(OrderProduct orderProduct);

    void removeOrderProductById(int id);

    OrderProduct getOrderProduct(int id);

    List<OrderProduct> getOrderProductByOrderId(int orderId);

    void updateOrderProduct(OrderProduct newOrderProduct, int id);

    Map validateOrderProduct(OrderProduct orderProduct);
}
