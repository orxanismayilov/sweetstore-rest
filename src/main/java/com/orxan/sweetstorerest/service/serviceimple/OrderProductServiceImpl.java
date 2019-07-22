package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.exceptions.InvalidOrderProductException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.OrderProductDaoImpl;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private  OrderProductDaoImpl orderProductDao;
    @Autowired
    private ProductService productService;
    @Value("${error.product.negativeQuantity}")
    private String negativeQuantity;
    @Value("${error.orderProduct.possibleQuantity}")
    private String possibleQuantity;
    @Value("${error.orderProduct.negativeTotalPrice}")
    private String negativeTotalPrice;
    @Value("${error.orderProduct.negativeDiscount}")
    private String negativeDiscount;
    @Override
    public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
        List<String> errorList=validateOrderProduct(orderProduct);
        if (errorList.isEmpty()){
            return orderProductDao.saveOrderProduct(orderProduct);
        }
        else throw new InvalidOrderProductException(errorList);
    }

    @Override
    public boolean removeOrderProductById(int id) {
        if (orderProductDao.isOrderProductExists(id)) {
            orderProductDao.removeOrderProductById(id);
            return true;
        } else throw new ResourceNotFoundException("OrderProduct not found.Id="+id);
    }

    @Override
    public OrderProduct getOrderProduct(int id) {
        if (orderProductDao==null) throw new ResourceNotFoundException("OrderProduct not found.Id="+id);
        return orderProductDao.getOrderProduct(id);
    }

    @Override
    public List<OrderProduct> getOrderProductByOrderId(int orderId) {
        List<OrderProduct> orderProductList=orderProductDao.getListByOrderId(orderId);
        if (orderProductList.isEmpty()) throw new ResourceNotFoundException("No OrderProduct found");
        return orderProductList;
    }

    @Override
    public OrderProduct updateOrderProduct(OrderProduct newOrderProduct, int id) {
        List<String> errorList=validateOrderProduct(newOrderProduct);
        if (errorList.isEmpty()) {
            orderProductDao.updateOrderProduct(newOrderProduct,id);
            OrderProduct orderProduct= orderProductDao.getOrderProduct(id);
            if (orderProduct==null) throw new ResourceNotFoundException("OrderProduct not found.Id="+id);
            return orderProduct;
        } else throw new InvalidOrderProductException(errorList);
    }

    @Override
    public List<String> validateOrderProduct(OrderProduct orderProduct) {
        List<String> errorList=new ArrayList<>();
        Product product= productService.getProductById(orderProduct.getProductId());;

        if (orderProduct!=null) {
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
        }
        return errorList;
    }
}
