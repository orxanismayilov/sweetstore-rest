package com.orxan.sweetstorerest.dtos;


import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private int id;
    private String customerName;
    private String customerAddress;
    private String description;
    private String orderType;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
    private String date;
    private String orderStatus;
    private boolean isActive;

    public OrderDTO() {
    }

    public OrderDTO(int id, String customerName, String customerAddress, String description, String orderType, BigDecimal totalPrice, BigDecimal totalDiscount, Date date, String orderStatus, boolean isActive) {
        this.id = id;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.description = description;
        this.orderType = orderType;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.date =String.valueOf(date);
        this.orderStatus = orderStatus;
        this.isActive = isActive;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", description='" + description + '\'' +
                ", orderType=" + orderType +
                ", id=" + id +
                ", totalPrice=" + totalPrice +
                ", totalDiscount=" + totalDiscount +
                ", date='" + date + '\'' +
                ", orderStatus=" + orderStatus +
                ", isActive=" + isActive +
                '}';
    }
}

