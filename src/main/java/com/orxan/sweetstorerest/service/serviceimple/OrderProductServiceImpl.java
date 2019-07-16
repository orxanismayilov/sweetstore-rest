package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.OrderProductDaoImpl;
import com.orxan.sweetstorerest.service.OrderProductService;
import com.orxan.sweetstorerest.service.ProductService;
import com.orxan.sweetstorerest.util.LoadPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private String ERROR_PROPERTIES="C:\\Users\\Orxan\\Desktop\\projects\\sweetstore-rest\\src\\main\\resources\\properties\\errors.properties";

    private final OrderProductDaoImpl orderProductDao;
    private ProductService productService;
    private Map<String,Map<Boolean,List<String>>> validation;
    private Properties properties;

    @Autowired
    public OrderProductServiceImpl(OrderProductDaoImpl orderProductDao,ProductService productService) {
        this.orderProductDao = orderProductDao;
        this.productService=productService;
        this.properties= LoadPropertyUtil.loadPropertiesFile(ERROR_PROPERTIES);
    }

    @Override
    public void saveOrderProduct(OrderProduct orderProduct) {
        orderProductDao.saveOrderProduct(orderProduct);
    }

    @Override
    public void removeOrderProductById(int id) {
        orderProductDao.removeOrderProductById(id);
    }

    @Override
    public OrderProduct getOrderProduct(int id) {
        return orderProductDao.getOrderProduct(id);
    }

    @Override
    public List<OrderProduct> getOrderProductByOrderId(int orderId) {
        List<OrderProduct> orderProductList=orderProductDao.getListByOrderId(orderId);
        return orderProductList;
    }

    @Override
    public void updateOrderProduct(OrderProduct newOrderProduct, int id) {
        orderProductDao.updateOrderProduct(newOrderProduct,id);
    }

    @Override
    public Map validateOrderProduct(OrderProduct orderProduct) {
        validation=new HashMap<>();
        Map<Boolean,List<String>> quantityMap=new HashMap<>();
        Map<Boolean,List<String>> discountMap=new HashMap<>();
        Map<Boolean,List<String>> totalPriceMap=new HashMap<>();
        totalPriceMap.put(false,new ArrayList<>());
        quantityMap.put(false,new ArrayList<>());
        discountMap.put(false,new ArrayList<>());
        Product product= productService.getProductById(orderProduct.getProductId());;

        if (orderProduct!=null) {
            List quantityList = quantityMap.get(false);
            List discountList = discountMap.get(false);
            List totalPriceList=totalPriceMap.get(false);
            if (product != null) {
                if (orderProduct.getProductQuantity() <= 0) {
                    quantityList.add(properties.getProperty("negativeQuantity"));
                }
                if (orderProduct.getProductQuantity()>product.getQuantity()){
                    quantityList.add(properties.getProperty("possibleQuantity")+"--Current quantity is :"+product.getQuantity());
                }
                if (orderProduct.getDiscount()<0){
                    discountList.add(properties.getProperty("negativeDiscount"));
                }
                if(0 > orderProduct.getTotalPrice().floatValue()){
                    totalPriceList.add(properties.getProperty("negativeTotalPrice"));
                }
            }
            if (!discountList.isEmpty()){
                discountMap.remove(false);
                discountMap.put(true,discountList);
            }

            if (!quantityList.isEmpty()){
                quantityMap.remove(false);
                quantityMap.put(true,quantityList);
            }

            if (!totalPriceList.isEmpty()){
                totalPriceMap.remove(false);
                totalPriceMap.put(true,totalPriceList);
            }
        }
        validation.put("quantityError",quantityMap);
        validation.put("discountError",discountMap);
        validation.put("totalPriceError",totalPriceMap);
        return validation;
    }
}
