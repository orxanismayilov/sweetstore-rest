package com.orxan.sweetstorerest.service.serviceimple;

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
    @Autowired
    private UserService userService;

    @Override
    public OrdersDTO getOrderList(int pageIndex, int rowsPerPage,String username) {
        if (userService.getUserRole(username).getCode()>=1){
            int totalCount=orderDao.getTotalCountOfOrder();
            int fromIndex=pageIndex*rowsPerPage;
            int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
            List<Order> orderList=orderDao.getOrderList(fromIndex,toIndex);
            OrdersDTO ordersDTO=new OrdersDTO();
            ordersDTO.setCount(orderDao.getTotalCountOfOrder());
            ordersDTO.setOrders(orderList);
            return ordersDTO;
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }

    @Override
    public Order addOrder(Order order, String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            int id = orderDao.addOrder(order);
            if (orderDao.isOrderExists(id)) {
                return orderDao.getOrder(id);
            }
            return null;
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }

    @Override
    public Order getOrder(int id,String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            Order order = orderDao.getOrder(id);
            if (order == null) throw new ResourceNotFoundException("Order not found. Id=" + id);
            BigDecimal totalDiscount = orderProductService.getTotalDiscount(id,username);
            if (totalDiscount != null) {
                order.setTotalDiscount(totalDiscount);
            } else {
                order.setTotalDiscount(new BigDecimal("0"));
            }
            return order;
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }

    @Override
    public List<Order> searchOrderById(String id, boolean searchAll,String username) {
        if (searchAll) {
            if (userService.getUserRole(username).getCode()>1) {
                return orderDao.searchOrderById(id, true);
            } else throw new PermissionDeniedException("You don't have permission for this action.");
        }  else if (userService.getUserRole(username).getCode()>=1) {
            return orderDao.searchOrderById(id, false);
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }

    @Override
    public boolean deleteOrderByTransactionId(int transactionId,String username) {
        if (userService.getUserRole(username).getCode()>1) {
            if (orderDao.isOrderExists(transactionId)) {
                orderDao.deleteOrderByTransactionId(transactionId);
                return true;
            } else throw new ResourceNotFoundException("Order not found.Id=" + transactionId);
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }

    @Override
    public Order updateOrderById(Order newOrder, int orderId,String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            orderDao.updateOrder(newOrder, orderId);
            return orderDao.getOrder(orderId);
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }

    @Override
    public int getTotalCountOfOrder(String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            return orderDao.getTotalCountOfOrder();
        } else {
            throw new PermissionDeniedException("You don't have permission for this action.");
        }
    }
}
