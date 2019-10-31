package com.orxan.sweetstorerest.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "ORDER_DETAILS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int transactionID;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "description")
    private String description;
    @Column(name = "order_type")
    private String orderType;
    @Column(name = "price_total")
    private BigDecimal totalPrice;
    @Column(name = "total_discount")
    private BigDecimal totalDiscount;
    @Column(name = "insert_date")
    private String date;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "is_active")
    private boolean isActive;

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
                ", transactionID=" + transactionID +
                ", totalPrice=" + totalPrice +
                ", totalDiscount=" + totalDiscount +
                ", date='" + date + '\'' +
                ", orderStatus=" + orderStatus +
                ", isActive=" + isActive +
                '}';
    }
}
