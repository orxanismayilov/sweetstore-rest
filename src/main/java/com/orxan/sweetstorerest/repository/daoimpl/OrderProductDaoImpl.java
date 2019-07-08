package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.model.OrderProduct;
import com.orxan.sweetstorerest.repository.OrderProductDao;
import com.orxan.sweetstorerest.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderProductDaoImpl implements OrderProductDao {
    @Override
    public List<OrderProduct> getListByOrderId(int orderId) {
        List<OrderProduct> orderProductsList=new ArrayList<>();
        String sql = "select \n" +
                "ORDER_PRODUCT.id,ORDER_PRODUCT.order_Id,ORDER_PRODUCT.product_id,PRODUCTS.name,ORDER_PRODUCT.price,ORDER_PRODUCT.quantity,\n" +
                "ORDER_PRODUCT.total_price,ORDER_PRODUCT.discount,ORDER_PRODUCT.description\n" +
                "from \n" +
                "ORDER_PRODUCT  \n" +
                "inner join PRODUCTS  on ORDER_PRODUCT.product_id=PRODUCTS.id \n" +
                "where order_id=? and ORDER_PRODUCT.is_active=1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    orderProduct.setActive(true);
                    orderProductsList.add(orderProduct);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderProductsList;
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
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    orderProduct.setActive(true);
                    return orderProduct;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeOrderProductById(int id) {
        String sql = "UPDATE ORDER_PRODUCT set is_active=0 where id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderProduct(OrderProduct newOrderProduct, int id) {
        String sql = "UPDATE ORDER_PRODUCT set quantity=?,discount=?,total_price=?,description=? where Id = ? ";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psOrderProduct = con.prepareStatement(sql)) {
            psOrderProduct.setInt(1, newOrderProduct.getProductQuantity());
            psOrderProduct.setFloat(2, newOrderProduct.getDiscount());
            psOrderProduct.setFloat(3, newOrderProduct.getTotalPrice().floatValue());
            psOrderProduct.setString(4, newOrderProduct.getDescription());
            psOrderProduct.setInt(5, id);
            psOrderProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
