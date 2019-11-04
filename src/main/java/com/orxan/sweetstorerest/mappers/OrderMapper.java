package com.orxan.sweetstorerest.mappers;


import com.orxan.sweetstorerest.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setCustomerName(resultSet.getString("customer_name"));
        order.setCustomerAddress(resultSet.getString("customer_address"));
        order.setDescription(resultSet.getString("description"));
        order.setOrderType(resultSet.getString("order_type"));
        order.setTotalPrice(resultSet.getBigDecimal("price_total"));
        order.setOrderStatus(resultSet.getString("order_status"));
        order.setActive(resultSet.getBoolean("is_active"));
        return order;
    }
}
