package com.orxan.sweetstorerest.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_PRODUCT")
public class OrderProduct  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "order_Id")
    private int orderId;
    @Column(name="product_Id")
    private int productId;
    @Column(name = "quantity")
    private int productQuantity;
    @Column(name = "price")
    private float productPrice;
    private BigDecimal totalPrice;
    private float discount;
    private String description;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false,insertable = false,updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable = false,insertable = false,updatable = false)
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                ", totalPrice=" + totalPrice +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
