package com.orxan.sweetstorerest.mappers;

import com.orxan.sweetstorerest.model.OrderProductSummary;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderProductSummaryMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderProductSummary summary=new OrderProductSummary();
        summary.setDescription(resultSet.getString("description"));
        summary.setTotalDiscount(resultSet.getBigDecimal("total_discount"));
        summary.setTotalPrice(resultSet.getBigDecimal("total_price"));
        return summary;
    }
}
