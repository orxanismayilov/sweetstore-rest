package com.orxan.sweetstorerest.mappers;

import com.orxan.sweetstorerest.dtos.OrderProductDTO;
import com.orxan.sweetstorerest.model.OrderProduct;

public class OrderProductMapper  {


    public OrderProductDTO mapOrderProductDTO(OrderProduct orderProduct) {
        OrderProductDTO dto=new OrderProductDTO();
        dto.setId(orderProduct.getId());
        dto.setOrderId(orderProduct.getOrderId());
        dto.setProductId(orderProduct.getProductId());
        dto.setProductName(orderProduct.getProduct().getName());
        dto.setProductQuantity(orderProduct.getProductQuantity());
        dto.setProductPrice(orderProduct.getProductPrice());
        dto.setTotalPrice(orderProduct.getTotalPrice());
        dto.setDiscount(orderProduct.getDiscount());
        dto.setDescription(orderProduct.getDescription());
        dto.setActive(orderProduct.isActive());
        return dto;
    }
    public OrderProductMapper orderProductMapper(){
        return new OrderProductMapper();
    }
}
