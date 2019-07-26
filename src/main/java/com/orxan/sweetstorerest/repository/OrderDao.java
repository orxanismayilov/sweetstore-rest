package com.orxan.sweetstorerest.repository;

import com.orxan.sweetstorerest.model.Order;

import java.util.List;

public interface OrderDao {

    List<Order> getOrderList(int pageIndex, int rowsPerPage);

    int addOrder(Order order) ;

    void updateOrder(Order newOrder, int oldOrderId);

    Order getOrder(int id);

    void deleteOrderByTransactionId(int transactionId);

    List<Order> searchOrderById(String id,boolean searchAll);

    int getTotalCountOfOrder() ;

    boolean isOrderExists(int id);


}
