package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.OrderProductDTO;
import com.orxan.sweetstorerest.dtos.OrderProductsDTO;
import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.exceptions.InvalidOrderProductException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.mappers.OrderProductMapper;
import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.repository.OrderProductJpaRepo;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final ProductService productService;
    private final OrderProductJpaRepo repo;
    private final OrderProductMapper mapper;

    @Value("${error.product.negativeQuantity}")
    private String negativeQuantity;
    @Value("${error.orderProduct.possibleQuantity}")
    private String possibleQuantity;
    @Value("${error.orderProduct.negativeTotalPrice}")
    private String negativeTotalPrice;
    @Value("${error.orderProduct.negativeDiscount}")
    private String negativeDiscount;

    @Autowired
    public OrderProductServiceImpl(ProductService productService, OrderProductJpaRepo repo, OrderProductMapper mapper) {
        this.productService = productService;
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
        List<String> errorList = validateOrderProduct(orderProduct);
        if (errorList.isEmpty()) {
            return repo.save(orderProduct);
        } else throw new InvalidOrderProductException(errorList);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public boolean removeOrderProductById(int id) {
        OrderProduct orderProduct = repo.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("OrderProduct not found.Id=" + id));
        orderProduct.setActive(false);
        repo.save(orderProduct);
        return true;
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderProductDTO getOrderProduct(int id) {
        return mapper.mapOrderProductDTO(repo.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("OrderProduct not found=" + id)));
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderProductsDTO getOrderProductByOrderId(int orderId) {
        OrderProductsDTO dto = new OrderProductsDTO();
        dto.setOrderProducts(repo.findByOrderId(orderId));
        dto.setSummary(repo.findOrderProductSummary(orderId));
        return dto;
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public OrderProductDTO updateOrderProduct(OrderProduct newOrderProduct, int id) {
        List<String> errorList = validateOrderProduct(newOrderProduct);
        if (errorList.isEmpty()) {
            newOrderProduct.setId(id);
            if (repo.existsById(id)) throw new ResourceNotFoundException("OrderProduct not found.Id=" + id);
            return mapper.mapOrderProductDTO(repo.save(newOrderProduct));
        } else throw new InvalidOrderProductException(errorList);
    }

    @Override
    @LoggerAnnotation
    public List<String> validateOrderProduct(OrderProduct orderProduct) {
        List<String> errorList = new ArrayList<>();
        ProductDTO product = productService.getProductById(orderProduct.getProductId());

        if (product != null) {
            if (orderProduct.getProductQuantity() <= 0) {
                errorList.add(negativeQuantity);
            }
            if (orderProduct.getProductQuantity() > product.getQuantity()) {
                errorList.add(possibleQuantity + "--Current quantity is :" + product.getQuantity());
            }
            if (orderProduct.getDiscount() < 0) {
                errorList.add(negativeDiscount);
            }
            if (0 > orderProduct.getTotalPrice().floatValue()) {
                errorList.add(negativeTotalPrice);
            }
        }
        return errorList;
    }

}
