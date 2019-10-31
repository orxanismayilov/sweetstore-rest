package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.exceptions.PermissionDeniedException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.OrderJpaRepo;
import com.orxan.sweetstorerest.repository.daoimpl.OrderDaoImpl;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.OrderService;
import com.orxan.sweetstorerest.service.UserService;
import org.modelmapper.ModelMapper;
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
    private OrderJpaRepo repo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    @LoggerAnnotation
    public OrdersDTO getOrderList(int pageIndex, int rowsPerPage,String username) {
            int totalCount=repo.countByIsActiveTrue();
            int fromIndex=pageIndex*rowsPerPage;
            int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
            OrdersDTO ordersDTO=new OrdersDTO();
            ordersDTO.setCount(repo.countByIsActiveTrue());
            ordersDTO.setOrders(repo.findByIsActiveTrue());
            return ordersDTO;
    }

    @Override
    @LoggerAnnotation
    public Order addOrder(Order order, String username) {
           return repo.save(order);
    }

    @Override
    @LoggerAnnotation
    public OrderDTO getOrder(int id,String username) {
        return modelMapper.map(repo.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. Id="+String.valueOf(id))),OrderDTO.class);
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
