package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.dtos.ProductsDTO;
import com.orxan.sweetstorerest.exceptions.InvalidProductException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.ProductDaoImpl;
import com.orxan.sweetstorerest.repository.daoimpl.ProductJpaRepo;
import com.orxan.sweetstorerest.service.ProductService;
import com.orxan.sweetstorerest.util.NumberUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoImpl productDao;
    @Autowired
    private ProductJpaRepo jpaRepo;
    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${error.product.nameSize}")
    private String nameSize;
    @Value("${error.product.nullName}")
    private String nullName;
    @Value("${error.product.negativeQuantity}")
    private String negativeQuantity;
    @Value("${error.product.maxQuantity}")
    private String maxQuantity;
    @Value("${error.product.invalidNumber}")
    private String invalidNumber;
    @Value("${error.product.negativePrice}")
    private String negativePrice;
    @Value("$}error.product.maxPrice}")
    private String maxPrice;

    @Override
    //@LoggerAnnotation
    public ProductsDTO getProductList(int pageIndex, int rowsPerPage, String username) {
        long startTime = System.currentTimeMillis();

        long usertime = System.currentTimeMillis();
        long diff = usertime - startTime;
        logger.info("time difference :" + diff);
        int totalCount = productDao.getTotalCountOfProduct();
        long time = System.currentTimeMillis();
        diff = time - usertime;
        logger.info("total count diff :" + diff);
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, totalCount);
        ProductsDTO productsDTO = new ProductsDTO();
        List<Product> productList = new ArrayList<>();
        jpaRepo.findAll().forEach(productList::add);
        long prList = System.currentTimeMillis();
        diff = prList - time;
        logger.info("time difference prList :" + diff);
        productsDTO.setProducts(productList);
        productsDTO.setCount(totalCount);
        long finalTime = System.currentTimeMillis();
        diff = finalTime - startTime;
        logger.info("result :" + diff);
        return productsDTO;
    }

    @Override
    @LoggerAnnotation
    public ProductDTO addProduct(Product product, String username) {
        List<String> errorList = isProductValid(product);
        if (errorList.isEmpty()) {
            Product checkProduct = jpaRepo.findFirstByName(product.getName());
            if (checkProduct == null) {
                product.setUpdateDate(LocalDateTime.now().toString());
                return modelMapper.map(jpaRepo.save(product), ProductDTO.class);
            } else {
                checkProduct.setQuantity(product.getQuantity() + checkProduct.getQuantity());
                checkProduct.setPrice(product.getPrice());
                jpaRepo.save(checkProduct);
            }
        } else
            throw new InvalidProductException(errorList);
        return null;
    }

    @Override
    @LoggerAnnotation
    public Product updateProduct(Product product, int oldProductId, String username) {
        if (productDao.isProductExist(oldProductId)) {
            List<String> errorList = isProductValid(product);
            if (errorList.isEmpty()) {
                return productDao.updateProduct(product, oldProductId);
            } else {
                throw new InvalidProductException(errorList);
            }
        } else {
            throw new ResourceNotFoundException("Product not found. Id=" + oldProductId);
        }
    }

    @Override
    public List<String> isProductValid(Product product) {
        List<String> errorList = new ArrayList();
        if (product != null) {
            if (product.getName() != null) {
                String name = product.getName();
                if (name.trim().length() < 2) {
                    errorList.add(nameSize);
                } else {
                    product.setName(renameProduct(product.getName()));
                }
            } else {
                errorList.add(nullName);
            }

            if (product.getQuantity() < 0) {
                errorList.add(negativeQuantity);
            }
            if (product.getQuantity() > 1000) {
                errorList.add(maxQuantity);
            }
            if (!NumberUtils.isNumberInteger(String.valueOf(product.getQuantity()))) {
                errorList.add(invalidNumber);
            }
            if (product.getPrice() < 0) {
                errorList.add(negativePrice);
            }
            if (product.getPrice() > 1000) {
                errorList.add(maxPrice);
            }
            if (!NumberUtils.isNumberFloat(String.valueOf(product.getPrice()))) {
                errorList.add(invalidNumber);
            }
        }
        return errorList;
    }

    @Override
    @LoggerAnnotation
    public boolean deleteProductByID(int id, String username) {
        boolean exist = productDao.isProductExist(id);
        if (exist) {
            productDao.deleteProductById(id);
            return true;
        } else throw new ResourceNotFoundException("Product not found. Id=" + id);
    }

    @Override
    @LoggerAnnotation
    public Product getProductById(int id, String username) {
        Product product = productDao.getProductById(id);
        if (product != null) {
            return product;
        } else throw new ResourceNotFoundException("Product not found. id=" + id);
    }

    @Override
    @LoggerAnnotation
    public List<Product> getProductListInStock(String username) {
        return productDao.getProductListForComboBox();
    }

    @Override
    @LoggerAnnotation
    public int getTotalCountOfProduct(String username) {
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
