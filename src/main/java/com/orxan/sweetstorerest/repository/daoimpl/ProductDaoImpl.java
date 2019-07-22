package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.mappers.ProductMapper;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.ProductDao;
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
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getProductList(int startIndex, int toIndex) {
        String sql = "SELECT * FROM PRODUCTS where is_active=1 ORDER BY id DESC Limit ?,? ";
        return jdbcTemplate.query(sql,new ProductMapper(),startIndex,toIndex);
    }

    @Override
    public List<Product> getProductListForComboBox() {
        String sql="SELECT * FROM PRODUCTS WHERE is_active=1 AND quantity>0 ORDER BY NAME ASC;";
        return jdbcTemplate.query(sql,new ProductMapper());
    }

    @Override
    public Product addProduct(Product product) {
        String sql = "INSERT INTO PRODUCTS (Name,Quantity,Price,Update_Date,updated_by,Is_Active) VALUES (?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setFloat(3, product.getPrice());
            ps.setTimestamp(4, product.getUpdateDate()==null ? Timestamp.valueOf(product.getUpdateDate()):Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(5, 1);
            ps.setBoolean(6,product.isActive());
            return ps;
        }, holder);
        return (Product) jdbcTemplate.queryForObject("SELECT * FROM PRODUCTS WHERE id=?",new ProductMapper(),holder.getKey().intValue());
    }

    @Override
    public void deleteProductById(int id) {
        String sql = "UPDATE PRODUCTS set Is_Active =0,update_date =? where Id = ? ";
        jdbcTemplate.update(sql,LocalDateTime.now(),id);
    }

    @Override
    public boolean isProductExist(int id) {
        String s = "SELECT COUNT(*) from PRODUCTS  where is_active = 1 AND id=? ";
       int count=jdbcTemplate.queryForObject(s,Integer.class,id);
       if (count>0) {
           return true;
       }
       return false;
    }

    @Override
    public void updateProductIncreaseQuantity(Product newProduct, int id) {
    }

    @Override
    public Product updateProduct(Product product, int oldProductId) {
        String sql = "UPDATE PRODUCTS set name=?,price=?,quantity=quantity+?,update_date=? where id=?";
        jdbcTemplate.update(sql,product.getName(),product.getPrice(),product.getQuantity(),LocalDateTime.now(),oldProductId);
        return (Product) jdbcTemplate.queryForObject("SELECT * FROM PRODUCTS WHERE id=?",new ProductMapper(),oldProductId);
    }

    @Override
    public Product getProductById(int id) {
        String sql="SELECT * FROM  PRODUCTS WHERE id=? and is_active=1";
        try {
            return (Product) jdbcTemplate.queryForObject(sql,new ProductMapper(),id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int getTotalCountOfProduct() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM PRODUCTS where is_active=1", Integer.class);
    }
}
