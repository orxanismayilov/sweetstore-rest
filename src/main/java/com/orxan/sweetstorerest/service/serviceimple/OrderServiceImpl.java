package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.OrderDTO;
import com.orxan.sweetstorerest.dtos.OrdersDTO;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.OrderJpaRepo;
import com.orxan.sweetstorerest.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderJpaRepo repo;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderJpaRepo repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }


    @Override
    @LoggerAnnotation
    public OrdersDTO getOrderList(int pageIndex, int rowsPerPage,String username) {
            OrdersDTO ordersDTO=new OrdersDTO();
            ordersDTO.setOrders(repo.findAllActiveOrders(createPageRequest(pageIndex,rowsPerPage)));
            ordersDTO.setCount(repo.countByIsActiveTrue());
            return ordersDTO;
    }

    @Override
    @LoggerAnnotation
    public OrderDTO addOrder(Order order, String username) {
        order.setUpdatedBy(1);
        return modelMapper.map(repo.save(order),OrderDTO.class);
    }

    @Override
    @LoggerAnnotation
    public OrderDTO getOrder(int id,String username) {
        return modelMapper.map(repo.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Order not found. Id="+id)),OrderDTO.class);
    }

    @Override
    @LoggerAnnotation
    public List<OrderDTO> searchOrderById(String id, boolean searchAll, String username) {
        return searchAll ? repo.findAllByIdLike(id) : repo.findByIdLikeAndIsActiveTrue(id);
    }

    @Override
    @LoggerAnnotation
    public boolean deleteOrderByTransactionId(int transactionId,String username) {
        Order order=repo.findByIdAndIsActiveTrue(transactionId).orElseThrow(()->new ResourceNotFoundException("Order not found. Id="+transactionId));
        order.setActive(false);
        repo.save(order);
        return true;
    }

    @Override
    @LoggerAnnotation
    public OrderDTO updateOrderById(Order newOrder, int orderId, String username) {
            newOrder.setId(orderId);
            return modelMapper.map(repo.save(newOrder),OrderDTO.class);
    }

    @Override
    @LoggerAnnotation
    public int getTotalCountOfOrder(String username) {
            return repo.countByIsActiveTrue();
    }

    private Pageable createPageRequest(int page, int rows) {
        return PageRequest.of(page,rows, Sort.Direction.DESC,"id");
    }
}
