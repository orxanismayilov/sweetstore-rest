package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.mappers.OrderProductMapper;
import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.repository.OrderProductDao;
import com.orxan.sweetstorerest.util.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderProductDaoImpl implements OrderProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderProduct> getListByOrderId(int orderId) {

        String sql = "select \n" +
                "ORDER_PRODUCT.id,ORDER_PRODUCT.order_Id,ORDER_PRODUCT.product_id,PRODUCTS.name,ORDER_PRODUCT.price,ORDER_PRODUCT.quantity,\n" +
                "ORDER_PRODUCT.total_price,ORDER_PRODUCT.discount,ORDER_PRODUCT.description\n" +
                "from \n" +
                "ORDER_PRODUCT  \n" +
                "inner join PRODUCTS  on ORDER_PRODUCT.product_id=PRODUCTS.id \n" +
                "where order_id=? and ORDER_PRODUCT.is_active=1";
       return jdbcTemplate.query(sql,new OrderProductMapper(),orderId);
    }

    @Override
    public void saveOrderProduct(OrderProduct orderProduct) {
        String sql = "INSERT INTO ORDER_PRODUCT (order_Id,product_Id,price,quantity,total_price,discount,description,is_active)" +
                "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psOrderProduct = con.prepareStatement(sql)) {
            psOrderProduct.setInt(1, orderProduct.getOrderId());
            psOrderProduct.setInt(2, orderProduct.getProductId());
            psOrderProduct.setFloat(3, orderProduct.getProductPrice());
            psOrderProduct.setInt(4, orderProduct.getProductQuantity());
            psOrderProduct.setFloat(5, orderProduct.getTotalPrice().floatValue());
            psOrderProduct.setFloat(6, orderProduct.getDiscount());
            psOrderProduct.setString(7, orderProduct.getDescription());
            psOrderProduct.setInt(8, orderProduct.isActive() ? 1 : 0);
            psOrderProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderProduct getOrderProduct(int id) {
        String sql = "select \n" +
                "ORDER_PRODUCT.id,ORDER_PRODUCT.order_Id,ORDER_PRODUCT.product_id,PRODUCTS.name,ORDER_PRODUCT.price,ORDER_PRODUCT.quantity,\n" +
                "ORDER_PRODUCT.total_price,ORDER_PRODUCT.discount,ORDER_PRODUCT.description\n" +
                "from \n" +
                "ORDER_PRODUCT  \n" +
                "inner join PRODUCTS  on ORDER_PRODUCT.product_id=PRODUCTS.id \n" +
                "where ORDER_PRODUCT.id=? and ORDER_PRODUCT.is_active=1";
        return (OrderProduct) jdbcTemplate.queryForObject(sql,new OrderProductMapper(),id);
    }

    @Override
    public void removeOrderProductById(int id) {
        String sql = "UPDATE ORDER_PRODUCT set is_active=0 where id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateOrderProduct(OrderProduct orderProduct, int id) {
        String sql = "UPDATE ORDER_PRODUCT set quantity=?,discount=?,total_price=?,description=? where Id = ? ";
        jdbcTemplate.update(sql,orderProduct.getProductQuantity(),orderProduct.getDiscount(),orderProduct.getTotalPrice(),orderProduct.getDescription(),id);
    }
}
