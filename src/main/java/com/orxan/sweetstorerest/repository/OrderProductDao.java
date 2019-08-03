package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.OrderProductSummary;

import java.math.BigDecimal;
import java.util.List;

public interface OrderProductDao {

    List<OrderProduct> getListByOrderId(int orderId);

    int saveOrderProduct(OrderProduct orderProduct);

    OrderProduct getOrderProduct(int id);

    void removeOrderProductById(int id);

    boolean isOrderProductExists(int id);

    void updateOrderProduct(OrderProduct newOrderProduct, int id);

    BigDecimal getTotalDiscount(int orderId);

    OrderProductSummary getOrderProductSummary(int orderId);
}
