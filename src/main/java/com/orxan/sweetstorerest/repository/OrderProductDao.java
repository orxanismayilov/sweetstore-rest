package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.OrderProduct;

import java.util.List;

public interface OrderProductDao {

    List<OrderProduct> getListByOrderId(int orderId);

    void saveOrderProduct(OrderProduct orderProduct);

    OrderProduct getOrderProduct(int id);

    void removeOrderProductById(int id);

    void updateOrderProduct(OrderProduct newOrderProduct, int id);
}
