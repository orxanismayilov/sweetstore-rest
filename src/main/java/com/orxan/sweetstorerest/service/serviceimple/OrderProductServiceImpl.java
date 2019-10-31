package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.OrderProductsDTO;
import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.exceptions.InvalidOrderProductException;
import com.orxan.sweetstorerest.exceptions.PermissionDeniedException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.OrderProductSummary;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.OrderJpaRepo;
import com.orxan.sweetstorerest.repository.daoimpl.OrderProductDaoImpl;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private  OrderProductDaoImpl orderProductDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderJpaRepo repo;

    @Value("${error.product.negativeQuantity}")
    private String negativeQuantity;
    @Value("${error.orderProduct.possibleQuantity}")
    private String possibleQuantity;
    @Value("${error.orderProduct.negativeTotalPrice}")
    private String negativeTotalPrice;
    @Value("${error.orderProduct.negativeDiscount}")
    private String negativeDiscount;

    @Override
    @LoggerAnnotation
    public OrderProduct saveOrderProduct(OrderProduct orderProduct,String username) {
            List<String> errorList = validateOrderProduct(orderProduct, username);
            if (errorList.isEmpty()) {
                int i = orderProductDao.saveOrderProduct(orderProduct);
                return orderProductDao.getOrderProduct(i);
            } else throw new InvalidOrderProductException(errorList);
    }

    @Override
    @LoggerAnnotation
    public boolean removeOrderProductById(int id,String username) {
            if (orderProductDao.isOrderProductExists(id)) {
                orderProductDao.removeOrderProductById(id);
                return true;
            } else throw new ResourceNotFoundException("OrderProduct not found.Id=" + id);
    }

    @Override
    @LoggerAnnotation
    public OrderProduct getOrderProduct(int id,String username) {
            if (orderProductDao == null) throw new ResourceNotFoundException("OrderProduct not found.Id=" + id);
            return orderProductDao.getOrderProduct(id);
    }

    @Override
    @LoggerAnnotation
    public OrderProductsDTO getOrderProductByOrderId(int orderId,String username) {
            List<OrderProduct> orderProductList = orderProductDao.getListByOrderId(orderId);
            OrderProductSummary summary = orderProductDao.getOrderProductSummary(orderId);
            OrderProductsDTO dto = new OrderProductsDTO();
            dto.setSummary(summary);
            dto.setOrderProducts(orderProductList);
            return dto;
    }

    @Override
    @LoggerAnnotation
    public OrderProduct updateOrderProduct(OrderProduct newOrderProduct, int id,String username) {
            List<String> errorList = validateOrderProduct(newOrderProduct, username);
            if (errorList.isEmpty()) {
                orderProductDao.updateOrderProduct(newOrderProduct, id);
                OrderProduct orderProduct = orderProductDao.getOrderProduct(id);
                if (orderProduct == null) throw new ResourceNotFoundException("OrderProduct not found.Id=" + id);
                return orderProduct;
            } else throw new InvalidOrderProductException(errorList);
    }

    @Override
    @LoggerAnnotation
    public List<String> validateOrderProduct(OrderProduct orderProduct,String username) {
        List<String> errorList=new ArrayList<>();
        ProductDTO product= productService.getProductById(orderProduct.getProductId(),"");

        if (product != null) {
            if (orderProduct.getProductQuantity() <= 0) {
                errorList.add(negativeQuantity);
            }
            if (orderProduct.getProductQuantity()>product.getQuantity()){
                errorList.add(possibleQuantity+"--Current quantity is :"+product.getQuantity());
            }
            if (orderProduct.getDiscount()<0){
                errorList.add(negativeDiscount);
            }
            if(0 > orderProduct.getTotalPrice().floatValue()){
                errorList.add(negativeTotalPrice);
            }
        }
        return errorList;
    }

    @Override
    @LoggerAnnotation
    public BigDecimal getTotalDiscount(int orderId,String username) {
            return orderProductDao.getTotalDiscount(orderId);
    }
}
