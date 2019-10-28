package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.exceptions.PermissionDeniedException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.daoimpl.OrderDaoImpl;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.OrderService;
import com.orxan.sweetstorerest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDaoImpl orderDao;
    @Autowired
    private OrderProductService orderProductService;

    @Override
    @LoggerAnnotation
    public OrdersDTO getOrderList(int pageIndex, int rowsPerPage,String username) {
            int totalCount=orderDao.getTotalCountOfOrder();
            int fromIndex=pageIndex*rowsPerPage;
            int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
            List<Order> orderList=orderDao.getOrderList(fromIndex,toIndex);
            OrdersDTO ordersDTO=new OrdersDTO();
            ordersDTO.setCount(orderDao.getTotalCountOfOrder());
            ordersDTO.setOrders(orderList);
            return ordersDTO;
    }

    @Override
    @LoggerAnnotation
    public Order addOrder(Order order, String username) {
            int id = orderDao.addOrder(order);
            if (orderDao.isOrderExists(id)) {
                return orderDao.getOrder(id);
            }
            return null;
    }

    @Override
    @LoggerAnnotation
    public Order getOrder(int id,String username) {
            Order order = orderDao.getOrder(id);
            if (order == null) throw new ResourceNotFoundException("Order not found. Id=" + id);
            BigDecimal totalDiscount = orderProductService.getTotalDiscount(id,username);
            if (totalDiscount != null) {
                order.setTotalDiscount(totalDiscount);
            } else {
                order.setTotalDiscount(new BigDecimal("0"));
            }
            return order;
    }

    @Override
    @LoggerAnnotation
    public List<Order> searchOrderById(String id, boolean searchAll,String username) {
        if (searchAll) {
                return orderDao.searchOrderById(id, true);
            } else throw new PermissionDeniedException("You don't have permission for this action.");
    }

    @Override
    @LoggerAnnotation
    public boolean deleteOrderByTransactionId(int transactionId,String username) {
            if (orderDao.isOrderExists(transactionId)) {
                orderDao.deleteOrderByTransactionId(transactionId);
                return true;
            } else throw new ResourceNotFoundException("Order not found.Id=" + transactionId);
    }

    @Override
    @LoggerAnnotation
    public Order updateOrderById(Order newOrder, int orderId,String username) {
            orderDao.updateOrder(newOrder, orderId);
            return orderDao.getOrder(orderId);
    }

    @Override
    @LoggerAnnotation
    public int getTotalCountOfOrder(String username) {
            return orderDao.getTotalCountOfOrder();
    }
}
