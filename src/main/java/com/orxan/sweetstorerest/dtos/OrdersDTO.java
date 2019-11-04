package com.orxan.sweetstorerest.dtos;

import java.util.List;

public class OrdersDTO {
    private int count;
    private List<OrderDTO> orders;

    public OrdersDTO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
