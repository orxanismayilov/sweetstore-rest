package com.orxan.sweetstorerest.dtos;

import java.util.List;

public class ProductsDTO {
    private  int count;
    private List<ProductDTO> products;

    public ProductsDTO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductsDTO{" +
                "count=" + count +
                ", products=" + products +
                '}';
    }
}
