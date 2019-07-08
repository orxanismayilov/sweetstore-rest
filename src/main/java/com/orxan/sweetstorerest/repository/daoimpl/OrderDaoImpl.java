package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.enums.OrderStatus;
import com.orxan.sweetstorerest.enums.OrderType;
import com.orxan.sweetstorerest.model.Order;
import com.orxan.sweetstorerest.repository.OrderDao;
import com.orxan.sweetstorerest.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> getOrderList(int pageIndex, int rowsPerPage) {
        String sql="select * from ORDER_DETAILS where is_active=1  ORDER by id desc Limit ?,?";
        List<Order> orderList= new ArrayList<>();
        int totalCount=getTotalCountOfOrder();
        int fromIndex=pageIndex*rowsPerPage;
        int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
        try (Connection conn= DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement(sql))
        {
            ps.setInt(1,fromIndex);
            ps.setInt(2,toIndex);

            try (ResultSet resultSet=ps.executeQuery()){
                while (resultSet.next()) {
                    Order order=new Order();
                    order.setTransactionID(resultSet.getInt("id"));
                    order.setCustomerName(resultSet.getString("customer_name"));
                    order.setCustomerAddress(resultSet.getString("customer_address"));
                    order.setDescription(resultSet.getString("description"));
                    order.setOrderType(OrderType.valueOf(resultSet.getString("order_type")));
                    order.setTotalPrice(new BigDecimal(String.valueOf(resultSet.getFloat("price_total"))));
                    order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                    if (resultSet.getTimestamp("insert_date")!= null) {
                        order.setDate(resultSet.getTimestamp("insert_date").toLocalDateTime());
                    }
                    order.setActive(true);
                    orderList.add(order);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public int addOrder(Order order) {
        String sql = "INSERT INTO ORDER_DETAILS (customer_name,customer_address,description,order_type,order_status,price_total,insert_date,updated_by,is_active)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        Timestamp ts=Timestamp.valueOf(LocalDateTime.now());
        try (Connection con = DBConnection.getConnection()
        ) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, order.getCustomerName());
                ps.setString(2, order.getCustomerAddress());
                ps.setString(3, order.getDescription());
                ps.setString(4, order.getOrderType().toString());
                ps.setString(5, OrderStatus.CLOSED.toString());
                ps.setFloat(6, order.getTotalPrice().floatValue());
                ps.setTimestamp(7,ts);
                ps.setInt(8, 1);
                ps.setInt(9, 1);

                int rowAffected = ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    int orderId = 0;
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }

                    if (rowAffected == 1) {
                        con.commit();
                        return orderId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Order getOrder(int id) {
        String sql="SELECT * FROM ORDER_DETAILS WHERE id=? AND is_Active=1;";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,id);
            try (ResultSet rs=ps.executeQuery()){
                while (rs.next()) {
                    Order order=new Order();
                    order.setTransactionID(rs.getInt("id"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setCustomerAddress(rs.getString("customer_address"));
                    order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
                    order.setOrderType(OrderType.valueOf(rs.getString("order_type")));
                    order.setTotalPrice(rs.getBigDecimal("price_total"));
                    order.setDescription(rs.getString("description"));
                    order.setActive(rs.getBoolean("is_active"));
                    order.setDate(rs.getTimestamp("insert_date").toLocalDateTime());
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrder(Order newOrder, int oldOrderId) {
        String sql="UPDATE ORDER_DETAILS set customer_name=?,customer_address=?,description=?,order_type=?,order_status=?,price_total=?,insert_date=?,updated_by=?,is_active=? where id=?";
        Timestamp ts=Timestamp.valueOf(LocalDateTime.now());
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setString(1,newOrder.getCustomerName());
            ps.setString(2,newOrder.getCustomerAddress());
            ps.setString(3,newOrder.getDescription());
            ps.setString(4,newOrder.getOrderType().toString());
            ps.setString(5,newOrder.getOrderStatus().toString());
            ps.setFloat(6,newOrder.getTotalPrice().floatValue());
            ps.setTimestamp(7,ts);
            ps.setInt(8,1);
            ps.setBoolean(9,newOrder.isActive());
            ps.setInt(10,oldOrderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderByTransactionId(int transactionId) {
        String sql="Update ORDER_DETAILS set is_active=0 where id=?";
        try (Connection connection=DBConnection.getConnection();
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setInt(1,transactionId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> searchOrderById(String id, boolean searchAll) {
        List<Order> searchResult=new ArrayList<>();
        String sc="%"+id+"%";
        String sql="SELECT * FROM ORDER_DETAILS WHERE id LIKE ? and is_active=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setString(1,sc);
            ps.setInt(2,searchAll ? 0:1);
            try (ResultSet resultSet=ps.executeQuery()) {
                while (resultSet.next()) {
                    Order order=new Order();
                    order.setTransactionID(resultSet.getInt("id"));
                    order.setCustomerName(resultSet.getString("customer_name"));
                    order.setCustomerAddress(resultSet.getString("customer_address"));
                    order.setDescription(resultSet.getString("description"));
                    order.setOrderType(OrderType.valueOf(resultSet.getString("order_type")));
                    order.setTotalPrice(new BigDecimal(String.valueOf(resultSet.getFloat("price_total"))));
                    order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                    if (resultSet.getTimestamp("insert_date")!=null){
                        order.setDate(resultSet.getTimestamp("insert_date").toLocalDateTime());
                    }
                    order.setActive(resultSet.getBoolean("is_active"));
                    searchResult.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    @Override
    public int getTotalCountOfOrder() {
        String sql="SELECT * FROM ORDER_DETAILS where is_active=1";
        int count =0;
        try (Connection conn= DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement(sql);
             ResultSet rs=ps.executeQuery();)
        {
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
