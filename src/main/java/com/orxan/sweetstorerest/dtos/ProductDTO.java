package com.orxan.sweetstorerest.dtos;

import java.util.Date;

public class ProductDTO {
    private int id;
    private String name;
    private int quantity;
    private String updateDate;
    private float price;
    private boolean isActive;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, int quantity, Date updateDate, float price, boolean isActive) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.updateDate = String.valueOf(updateDate);
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

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
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
