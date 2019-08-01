package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
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
    public OrdersDTO getOrderList(int pageIndex, int rowsPerPage) {
        int totalCount=orderDao.getTotalCountOfOrder();
        int fromIndex=pageIndex*rowsPerPage;
        int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
        List<Order> orderList=orderDao.getOrderList(fromIndex,toIndex);
        OrdersDTO ordersDTO=new OrdersDTO();
        ordersDTO.setCount(orderDao.getTotalCountOfOrder());
        ordersDTO.setOrders(orderList);
        if (orderList.isEmpty()) throw new ResourceNotFoundException("No products found.");
        return ordersDTO;
    }

    @Override
    public Order addNewOrderToList(Order order) {
       int id= orderDao.addOrder(order);
       if (orderDao.isOrderExists(id)) {
           return orderDao.getOrder(id);
       }
       return null;
    }

    @Override
    public Order getOrder(int id) {
        Order order=orderDao.getOrder(id);
        if (order==null) throw new ResourceNotFoundException("Order not found. Id="+id);
        return order;
    }

    @Override
    public List<Order> searchOrderById(String id, boolean searchAll) {
        return orderDao.searchOrderById(id,searchAll);
    }

    @Override
    public boolean deleteOrderByTransactionId(int transactionId) {
        if (orderDao.isOrderExists(transactionId)) {
            orderDao.deleteOrderByTransactionId(transactionId);
            return true;
        } else throw new ResourceNotFoundException("Order not found.Id="+transactionId);
    }

    @Override
    public Order updateOrderById(Order newOrder, int orderId) {
        orderDao.updateOrder(newOrder,orderId);
        return orderDao.getOrder(orderId);
    }

    @Override
    public int getTotalCountOfOrder() {
        return orderDao.getTotalCountOfOrder();
    }
}
