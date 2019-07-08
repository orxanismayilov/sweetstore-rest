package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.daoimpl.OrderDaoImpl;
import com.orxan.sweetstorerest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDaoImpl orderDao;

    @Override
    public List<Order> getOrderList(int pageIndex, int rowsPerPage) {
        return orderDao.getOrderList(pageIndex,rowsPerPage);
    }

    @Override
    public int addNewOrderToList(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public Order getOrder(int id) {
        return orderDao.getOrder(id);
    }

    @Override
    public List<Order> searchOrderById(String id, boolean searchAll) {
        return orderDao.searchOrderById(id,searchAll);
    }

    @Override
    public boolean deleteOrderByTransactionId(int transactionId) {
        orderDao.deleteOrderByTransactionId(transactionId);
        return true;
    }

    @Override
    public void updateOrderById(Order newOrder, int orderId) {
        orderDao.updateOrder(newOrder,orderId);
    }

    @Override
    public int getTotalCountOfOrder() {
        return orderDao.getTotalCountOfOrder();
    }
}
