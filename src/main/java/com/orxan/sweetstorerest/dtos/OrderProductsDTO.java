package com.orxan.sweetstorerest.dtos;

import com.orxan.sweetstorerest.model.Summary;

import java.util.List;

public class OrderProductsDTO {
    private List<OrderProductDTO> orderProducts;
    private Summary summary;

    public OrderProductsDTO() {
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }
}
