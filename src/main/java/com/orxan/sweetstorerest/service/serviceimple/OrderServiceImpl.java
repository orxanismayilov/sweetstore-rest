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
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrdersDTO getOrderList(int pageIndex, int rowsPerPage) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setOrders(repo.findAllActiveOrders(createPageRequest(pageIndex, rowsPerPage)));
        ordersDTO.setCount(repo.countByIsActiveTrue());
        return ordersDTO;
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderDTO addOrder(Order order) {
        order.setUpdatedBy(1);
        return modelMapper.map(repo.save(order), OrderDTO.class);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderDTO getOrder(int id) {
        return modelMapper.map(repo.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Order not found. Id=" + id)), OrderDTO.class);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public List<OrderDTO> searchOrderById(String id, boolean searchAll) {
        return searchAll ? repo.findAllByIdLike(id) : repo.findByIdLikeAndIsActiveTrue(id);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN"})
    public void deleteOrderByTransactionId(int transactionId) {
        Order order = repo.findByIdAndIsActiveTrue(transactionId).orElseThrow(() -> new ResourceNotFoundException("Order not found. Id=" + transactionId));
        order.setActive(false);
        repo.save(order);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderDTO updateOrderById(Order newOrder, int orderId) {
        newOrder.setId(orderId);
        return modelMapper.map(repo.save(newOrder), OrderDTO.class);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public int getTotalCountOfOrder() {
        return repo.countByIsActiveTrue();
    }

    private Pageable createPageRequest(int page, int rows) {
        return PageRequest.of(page, rows, Sort.Direction.DESC, "id");
    }
}
