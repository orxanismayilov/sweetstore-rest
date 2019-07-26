package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.mappers.OrderMapper;
import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> getOrderList(int fromIndex, int toIndex) {
        String sql = "SELECT * FROM ORDER_DETAILS WHERE  is_active=1 LIMIT ?,?;";
        return jdbcTemplate.query(sql, new OrderMapper(),fromIndex,toIndex);

    }

    @Override
    public int addOrder(Order order) {
        String sql = "INSERT INTO ORDER_DETAILS (customer_name,customer_address,description,order_type,order_status,price_total,insert_date,updated_by,is_active)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getCustomerName());
            ps.setString(2, order.getCustomerAddress());
            ps.setString(3, order.getDescription());
            ps.setString(4, order.getOrderType().toString());
            ps.setString(5, order.getOrderStatus().toString());
            ps.setFloat(6, order.getTotalPrice().floatValue());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(8, 2);
            ps.setBoolean(9, order.isActive());
            return ps;
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public Order getOrder(int id) {
        String sql = "SELECT * FROM ORDER_DETAILS WHERE is_active=1 and id=?;";
        try {
            return (Order) jdbcTemplate.queryForObject(sql,new OrderMapper(),id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void updateOrder(Order order, int oldOrderId) {
        String sql = "UPDATE ORDER_DETAILS set customer_name=?,customer_address=?,description=?,order_type=?,order_status=?,price_total=?,insert_date=?,updated_by=?,is_active=? where id=?";
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
        jdbcTemplate.update(sql,order.getCustomerName(),order.getCustomerAddress(),order.getDescription(),order.getOrderType().toString(),order.getOrderStatus().toString(),order.getTotalPrice().floatValue(),ts,2,order.isActive()? 1:0,oldOrderId);
    }

    @Override
    public void deleteOrderByTransactionId(int transactionId) {
        String sql = "Update ORDER_DETAILS set is_active=0 where id=?";
        jdbcTemplate.update(sql,transactionId);
    }

    @Override
    public List<Order> searchOrderById(String id, boolean searchAll) {
        String sql = "SELECT * FROM ORDER_DETAILS WHERE id LIKE ? and is_active=?";
       return jdbcTemplate.query(sql,new OrderMapper(),id == null ? "%%" : "%" + id + "%",1);
    }



    @Override
    public int getTotalCountOfOrder() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ORDER_DETAILS where is_active=1", Integer.class);
    }

    @Override
    public boolean isOrderExists(int id) {
        String sql="SELECT COUNT(*) FROM ORDER_DETAILS WHERE id=? AND is_Active=1";
        int count=jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count>0;
    }
}