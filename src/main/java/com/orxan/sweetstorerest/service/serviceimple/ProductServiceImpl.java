package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.ProductDaoImpl;
import com.orxan.sweetstorerest.service.ProductService;
import com.orxan.sweetstorerest.util.GetValidationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Product addProduct(Product product) {
        product.setUpdateDate(LocalDateTime.now());
        return productDao.addProduct(product);
    }

    @Override
    public Product updateProduct(Product product, int oldProductId) {
        product.setUpdateDate(LocalDateTime.now());
        List<String> errors= GetValidationList.copyValuesToList(isProductValid(product));
        if (errors.isEmpty()) {
            return productDao.updateProduct(product, oldProductId);
        }else {
            throw new IllegalArgumentException();
        }
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
        Product product=productDao.getProductById(id);
       return product;
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
