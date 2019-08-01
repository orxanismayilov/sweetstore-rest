package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.model.Order;

import java.util.List;

public interface OrderService {

    OrdersDTO getOrderList(int pageIndex, int rowsPerPage);

    Order addNewOrderToList(Order order);

    List<Order> searchOrderById(String id, boolean searchAll);

    Order getOrder(int id);

    boolean deleteOrderByTransactionId(int transactionId);

    Order updateOrderById(Order newOrder, int orderId);

    int getTotalCountOfOrder();
}
