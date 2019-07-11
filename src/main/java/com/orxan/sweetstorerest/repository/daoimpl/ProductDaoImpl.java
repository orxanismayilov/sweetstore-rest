package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.mappers.ProductMapper;
import com.orxan.sweetstorerest.mappers.UserMapper;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getProductList(int startIndex, int toIndex) {
        String sql = "SELECT * FROM PRODUCTS where is_active=1 ORDER BY id DESC Limit ?,? ";
        return jdbcTemplate.query(sql,new UserMapper(),startIndex,toIndex);
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO PRODUCTS (Name,Quantity,Price,Update_Date,updated_by,Is_Active) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,product.getName(),product.getPrice(),product.getUpdateDate()==null ? product.getUpdateDate():LocalDateTime.now(),
                1,product.isActive() ? 1:0);
    }

    @Override
    public void deleteProductById(int id) {
        String sql = "UPDATE PRODUCTS set Is_Active =0,update_date =? where Id = ? ";
        jdbcTemplate.update(sql,LocalDateTime.now(),id);
    }

    @Override
    public Product isProductExist(String name) {
        String s = "SELECT * from PRODUCTS  where Name = ? ";
        return (Product) jdbcTemplate.queryForObject(s,new ProductMapper(),name);
    }

    @Override
    public void updateProductIncreaseQuantity(Product newProduct, int id) {
    }

    @Override
    public void updateProduct(Product product, int oldProductId) {
        String sql = "UPDATE PRODUCTS set name=?,price=?,quantity=?,update_date=? where id=?";
        jdbcTemplate.update(sql,product.getName(),product.getPrice(),product.getQuantity(),LocalDateTime.now(),oldProductId);
    }

    @Override
    public Product getProductById(int id) {
        String sql="SELECT * FROM  PRODUCTS WHERE id=? and is_active=1";
        return (Product) jdbcTemplate.queryForObject(sql,new ProductMapper(),id);
    }

    @Override
    public int getTotalCountOfProduct() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ORDER_DETAILS where is_active=1", Integer.class);
    }
}
