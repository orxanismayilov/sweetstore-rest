package com.orxan.sweetstorerest.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int quantity;
    private Date updateDate;
    private float price;
    private boolean isActive;
    private int updatedBy;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = new Date();
    }

    @PrePersist
    public void onCreate() {
        this.updateDate = new Date();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", updateDate='" + updateDate + '\'' +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }
}
