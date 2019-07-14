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
        int totalCount= getTotalCountOfProduct();
        int fromIndex=pageIndex*rowsPerPage;
        int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
        return productDao.getProductList(fromIndex,toIndex);
    }

    @Override
    public Map addProduct(Product product) {
        productDao.addProduct(product);
        return null;
    }

    @Override
    public Map updateProduct(Product product, int oldProductId) {
        productDao.updateProduct(product,oldProductId);
        return null;
    }

    @Override
    public Map<String, Map<Boolean, List<String>>> isProductValid(Product product) {
        return null;
    }

    @Override
    public boolean deleteProductByID(int id) {
        productDao.deleteProductById(id);
        return false;
    }

    @Override
    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    @Override
    public List<Product> getProductListForComboBox() {
        return productDao.getProductListForComboBox();
    }

    @Override
    public int getTotalCountOfProduct() {
        return productDao.getTotalCountOfProduct();
    }
}
