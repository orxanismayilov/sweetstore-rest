package com.orxan.sweetstorerest.mappers;

import com.orxan.sweetstorerest.model.OrderProduct;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderProductMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(rs.getInt("id"));
        orderProduct.setOrderId(rs.getInt("order_id"));
        orderProduct.setProductId(rs.getInt("product_id"));
        orderProduct.setProductName(rs.getString("name"));
        orderProduct.setProductPrice(rs.getFloat("price"));
        orderProduct.setProductQuantity(rs.getInt("quantity"));
        orderProduct.setTotalPrice(new BigDecimal(String.valueOf(rs.getFloat("total_price"))));
        orderProduct.setDiscount(rs.getFloat("discount"));
        orderProduct.setDescription(rs.getString("description"));
        orderProduct.setActive(rs.getBoolean("is_active"));
        return orderProduct;
    }
}
