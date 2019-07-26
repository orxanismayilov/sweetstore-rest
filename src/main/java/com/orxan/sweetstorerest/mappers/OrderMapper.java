package com.orxan.sweetstorerest.mappers;


import com.orxan.sweetstorerest.enums.OrderStatus;
import com.orxan.sweetstorerest.enums.OrderType;
import com.orxan.sweetstorerest.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setTransactionID(resultSet.getInt("id"));
        order.setCustomerName(resultSet.getString("customer_name"));
        order.setCustomerAddress(resultSet.getString("customer_address"));
        order.setDescription(resultSet.getString("description"));
        order.setOrderType(OrderType.valueOf(resultSet.getString("order_type")));
        order.setTotalPrice(resultSet.getBigDecimal("price_total"));
        order.setDate(resultSet.getTimestamp("insert_date").toString());
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
        order.setActive(true);
        return order;
    }
}
