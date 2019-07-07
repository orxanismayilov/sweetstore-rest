package com.orxan.sweetstorerest.model;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private LocalDateTime updateDate;
    private float price;
    private boolean isActive;

    public Product() {
    }

    public Product(int id, String name, int quantity, LocalDateTime updateDate, float price, boolean isActive) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.updateDate = updateDate;
        this.price = price;
        this.isActive = isActive;
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

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
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
}
