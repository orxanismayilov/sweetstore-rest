package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.model.Order;

import java.util.List;

public interface OrderService {

    OrdersDTO getOrderList(int pageIndex, int rowsPerPage);

    OrderDTO addOrder(Order order);

    List<OrderDTO> searchOrderById(String id, boolean searchAll);

    OrderDTO getOrder(int id);

    void deleteOrderByTransactionId(int transactionId);

    OrderDTO updateOrderById(Order newOrder, int orderId);

    int getTotalCountOfOrder();
}
