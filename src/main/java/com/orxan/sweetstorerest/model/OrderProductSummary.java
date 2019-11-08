package com.orxan.sweetstorerest.model;

import java.math.BigDecimal;

public class OrderProductSummary {
    private String description;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;

    public OrderProductSummary() {
    }

    public OrderProductSummary(String description,BigDecimal totalPrice, double totalDiscount) {
        this.description=this.description+description+",";
        this.totalPrice = totalPrice;
        this.totalDiscount = new BigDecimal(totalDiscount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
