package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.ProductDaoImpl;
import com.orxan.sweetstorerest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoImpl productDao;

    @Override
    public List<Product> getProductList(int pageIndex, int rowsPerPage) {
        return productDao.getProductList(pageIndex,rowsPerPage);
    }

    @Override
    public Map addProduct(Product product) {
        return null;
    }

    @Override
    public Map updateProduct(Product product, int oldProductId) {
        return null;
    }

    @Override
    public Map<String, Map<Boolean, List<String>>> isProductValid(Product product) {
        return null;
    }

    @Override
    public boolean deleteProductByID(int id) {
        return false;
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
