package com.orxan.sweetstorerest.dtos;

import com.orxan.sweetstorerest.model.OrderProductSummary;

import java.util.List;

public class OrderProductsDTO {
    private List<OrderProductDTO> orderProducts;
    private OrderProductSummary summary;

    public OrderProductsDTO() {
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public OrderProductSummary getSummary() {
        return summary;
    }

    public void setSummary(OrderProductSummary summary) {
        this.summary = summary;
    }
}
