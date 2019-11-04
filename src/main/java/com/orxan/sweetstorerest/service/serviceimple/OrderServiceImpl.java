package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.OrderJpaRepo;
import com.orxan.sweetstorerest.repository.daoimpl.OrderDaoImpl;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            ordersDTO.setOrders(new ArrayList<>());
            ordersDTO.setCount(repo.countByIsActiveTrue());
            for (Order order:repo.findByIsActiveTrue()) {
                ordersDTO.getOrders().add(modelMapper.map(order,OrderDTO.class));
            }
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
    public List<Order> searchOrderById(int id, boolean searchAll,String username) {
        return repo.findByIdLikeAndIsActiveTrue(id);
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
