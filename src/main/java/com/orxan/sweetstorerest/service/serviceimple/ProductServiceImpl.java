package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.exceptions.InvalidProductException;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.ProductDaoImpl;
import com.orxan.sweetstorerest.service.ProductService;
import com.orxan.sweetstorerest.util.LoadPropertyUtil;
import com.orxan.sweetstorerest.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoImpl productDao;
    private Properties errorProperties;
    private String ERROR_PROPERTIES_URL="properties/errors.properties";
    private static Map<String, Map<Boolean, List<String>>> validation;

    public ProductServiceImpl() {
        this.errorProperties = LoadPropertyUtil.loadPropertiesFile(ERROR_PROPERTIES_URL);
    }

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
        List<String> errorList= isProductValid(product);
        if (errorList.isEmpty()) {
            return productDao.addProduct(product);
        }else {
            throw new InvalidProductException(errorList);
        }
    }

    @Override
    public Product updateProduct(Product product, int oldProductId) {
        product.setUpdateDate(LocalDateTime.now());
        List<String> errorList= isProductValid(product);
        if (errorList.isEmpty()) {
            return productDao.updateProduct(product, oldProductId);
        }else {
            throw new InvalidProductException(errorList);
        }
    }

    @Override
    public List<String> isProductValid(Product product) {
        List<String> errorList = new ArrayList();
        if (product != null) {
            if (product.getName() != null) {
                String name = product.getName();
                if (name.trim().length() < 2) {
                    errorList.add(errorProperties.getProperty("nameSize"));
                } else {
                    product.setName(renameProduct(product.getName()));
                }
            } else {
                errorList.add(errorProperties.getProperty("nullName"));
            }

            if (product.getQuantity() < 0) {
                errorList.add(errorProperties.getProperty("negativeQuantity"));
            }
            if (product.getQuantity() > 1000) {
                errorList.add(errorProperties.getProperty("maxQuantity"));
            }
            if (!NumberUtils.isNumberInteger(String.valueOf(product.getQuantity()))) {
                errorList.add(errorProperties.getProperty("invalidNumber"));
            }
            if (product.getPrice() < 0) {
                errorList.add(errorProperties.getProperty("negativePrice"));
            }
            if (product.getPrice() > 1000) {
                errorList.add(errorProperties.getProperty("maxPrice"));
            }
            if (!NumberUtils.isNumberFloat(String.valueOf(product.getPrice()))) {
                errorList.add(errorProperties.getProperty("invalidNumber"));
            }
        }
        return errorList;
    }

    @Override
    public boolean deleteProductByID(int id) {
        return productDao.deleteProductById(id);
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

    private String renameProduct(String productName) {
        String finalName = "";
        if (productName != null) {
            finalName = productName.toLowerCase().trim();
        }
        finalName = finalName.substring(0, 1).toUpperCase() + finalName.substring(1);
        return finalName;
    }
}
