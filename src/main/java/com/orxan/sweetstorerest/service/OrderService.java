package com.orxan.sweetstorerest.service;

import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.model.Order;

import java.util.List;

public interface OrderService {

    OrdersDTO getOrderList(int pageIndex, int rowsPerPage,String username);

    OrderDTO addOrder(Order order, String username);

    List<OrderDTO> searchOrderById(String id, boolean searchAll, String username);

    OrderDTO getOrder(int id, String username);

    boolean deleteOrderByTransactionId(int transactionId,String username);

    OrderDTO updateOrderById(Order newOrder, int orderId, String username);

    int getTotalCountOfOrder(String username);
}
