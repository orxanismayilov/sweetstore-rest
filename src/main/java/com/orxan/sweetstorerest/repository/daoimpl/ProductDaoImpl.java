package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.ProductDao;
import com.orxan.sweetstorerest.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Product> getProductList(int pageIndex, int rowsPerPage) {
        List<Product> productList =new ArrayList<>();
        String sql = "SELECT * FROM Products where is_active=1 ORDER BY id DESC Limit ?,? ";
        int totalCount= getTotalCountOfProduct();
        int fromIndex=pageIndex*rowsPerPage;
        int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, fromIndex);
            ps.setInt(2,toIndex);
            try (ResultSet rs=ps.executeQuery()){
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("Id"));
                    product.setName(rs.getString("Name"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setPrice(rs.getFloat("Price"));
                    if (rs.getTimestamp("update_date")!=null) {
                        product.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime());
                    }
                    product.setActive(true);
                    productList.add(product);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (Name,Quantity,Price,Update_Date,Is_Active) VALUES (?,?,?,?,?)";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(product.getUpdateDate()));
            preparedStatement.setInt(5, 1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProductById(int id) {

    }

    @Override
    public Product isProductExist(String name) {
        return null;
    }

    @Override
    public void updateProductIncreaseQuantity(Product newProduct, int id) {

    }

    @Override
    public void updateProduct(Product product, int oldProductId) {

    }

    @Override
    public Product getProductById(int id) {
        return null;
    }

    @Override
    public int getTotalCountOfProduct() {
        return 0;
    }
}
