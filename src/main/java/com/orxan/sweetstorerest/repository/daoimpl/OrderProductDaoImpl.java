package com.orxan.sweetstorerest.repository.daoimpl;
import com.orxan.sweetstorerest.mappers.OrderProductMapper;
import com.orxan.sweetstorerest.mappers.OrderProductSummaryMapper;
import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.model.OrderProductSummary;
import com.orxan.sweetstorerest.repository.OrderProductDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderProductDaoImpl implements OrderProductDao {

    private static Logger logger= LogManager.getLogger(OrderProductDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderProduct> getListByOrderId(int orderId) {
        String sql = "select op.id,op.order_Id,op.product_id,p.name,op.price,op.quantity,op.total_price,op.discount,op.description,op.is_active from ORDER_PRODUCT op inner join PRODUCTS p  on op.product_id=p.id where op.order_id=? and op.is_active=1;";
       return new ArrayList<>();//jdbcTemplate.query(sql,new OrderProductMapper(),orderId);
    }

    @Override
    public int saveOrderProduct(OrderProduct orderProduct) {
        String sql = "INSERT INTO ORDER_PRODUCT (order_Id,product_Id,price,quantity,total_price,discount,description,is_active)" +
                "VALUES (?,?,?,?,?,?,?,?)";
        KeyHolder holder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,orderProduct.getOrderId());
            ps.setInt(2,orderProduct.getProductId());
            ps.setFloat(3,orderProduct.getProductPrice());
            ps.setInt(4,orderProduct.getProductQuantity());
            ps.setFloat(5,orderProduct.getTotalPrice().floatValue());
            ps.setFloat(6,orderProduct.getDiscount());
            ps.setString(7,orderProduct.getDescription());
            ps.setBoolean(8,true);
            return ps;
        },holder);
        return holder.getKey().intValue();
    }

    @Override
    public OrderProduct getOrderProduct(int id) {
        String sql = "select op.id,op.order_Id,op.product_id,p.name,op.price,op.quantity,op.total_price,op.discount,op.description,op.is_active\n" +
                "from ORDER_PRODUCT as op inner join PRODUCTS as p on op.product_id=p.id where op.id=? AND op.is_active=1;";
       try {
           return new OrderProduct();// (OrderProduct) jdbcTemplate.queryForObject(sql,new OrderProductMapper(),id);
       } catch (EmptyResultDataAccessException e) {
           logger.error(e.getMessage());
           return null;
       }
    }

    @Override
    public void removeOrderProductById(int id) {
        String sql = "UPDATE ORDER_PRODUCT set is_active=0 where id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public boolean isOrderProductExists(int id) {
        String sql="SELECT COUNT(*) FROM ORDER_PRODUCT WHERE id=? AND is_active=1;";
        int count=jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count > 0;
    }

    @Override
    public void updateOrderProduct(OrderProduct orderProduct, int id) {
        String sql = "UPDATE ORDER_PRODUCT set quantity=?,discount=?,total_price=?,description=? where Id = ? ";
        jdbcTemplate.update(sql,orderProduct.getProductQuantity(),orderProduct.getDiscount(),orderProduct.getTotalPrice().floatValue(),orderProduct.getDescription(),id);
    }

    @Override
    public BigDecimal getTotalDiscount(int orderId) {
        String sql="SELECT SUM(discount) FROM ORDER_PRODUCT WHERE order_Id=? AND is_active=1;";
        return jdbcTemplate.queryForObject(sql,BigDecimal.class,orderId);
    }

    @Override
    public OrderProductSummary getOrderProductSummary(int orderId) {
        String sql="SELECT SUM(op.discount) AS total_discount,SUM(op.total_price) AS total_price,GROUP_CONCAT(op.description separator ',') AS description FROM order_product op WHERE op.order_Id=? AND op.is_active=1;";
        try {
           return (OrderProductSummary) jdbcTemplate.queryForObject(sql,new OrderProductSummaryMapper(),orderId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
