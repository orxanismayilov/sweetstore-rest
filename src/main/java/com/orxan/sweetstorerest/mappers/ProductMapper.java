package com.orxan.sweetstorerest.mappers;

import com.orxan.sweetstorerest.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Product product=new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getFloat("price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setActive(true);
        return product;
    }
}
