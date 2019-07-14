package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrderList(int pageIndex, int rowsPerPage);

    int addNewOrderToList(Order order);

    List<Order> searchOrderById(String id, boolean searchAll);

    Order getOrder(int id);

    boolean deleteOrderByTransactionId(int transactionId);

    void updateOrderById(Order newOrder, int orderId);

    int getTotalCountOfOrder();
}
