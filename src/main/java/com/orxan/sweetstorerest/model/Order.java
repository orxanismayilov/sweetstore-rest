package com.orxan.sweetstorerest.model;

import com.orxan.sweetstorerest.enums.OrderStatus;
import com.orxan.sweetstorerest.enums.OrderType;

import java.math.BigDecimal;

public class Order {
    private String customerName;
    private String customerAddress;
    private String description;
    private OrderType orderType;
    private int transactionID;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
    private String date;
    private OrderStatus orderStatus;
    private boolean isActive;

    public Order() {
    }

    public Order(String customerName, String customerAddress, String description, OrderType orderType, int transactionID, BigDecimal totalPrice, BigDecimal totalDiscount, String date, OrderStatus orderStatus, boolean isActive) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.description = description;
        this.orderType = orderType;
        this.transactionID = transactionID;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.date = date;
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

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
