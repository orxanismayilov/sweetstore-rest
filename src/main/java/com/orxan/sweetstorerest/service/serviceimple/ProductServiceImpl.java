package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.aop.LoggerAnnotation;
import com.orxan.sweetstorerest.dtos.ProductDTO;
import com.orxan.sweetstorerest.dtos.ProductsDTO;
import com.orxan.sweetstorerest.exceptions.InvalidProductException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.ProductJpaRepo;
import com.orxan.sweetstorerest.service.ProductService;
import com.orxan.sweetstorerest.util.NumberUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

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
    @LoggerAnnotation
    public ProductsDTO getProductList(int pageIndex, int rowsPerPage, String username) {
        long startTime = System.currentTimeMillis();

        long usertime = System.currentTimeMillis();
        long diff = usertime - startTime;
        //logger.info("time difference :" + diff);
        int totalCount = getTotalCountOfProduct(username);
        long time = System.currentTimeMillis();
        diff = time - usertime;
        //logger.info("total count diff :" + diff);
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, totalCount);
        ProductsDTO productsDTO = new ProductsDTO();
        long prList = System.currentTimeMillis();
        diff = prList - time;
        //logger.info("time difference prList :" + diff);
        productsDTO.setProducts(jpaRepo.findByIsActiveTrue());
        productsDTO.setCount(totalCount);
        long finalTime = System.currentTimeMillis();
        diff = finalTime - startTime;
        //logger.info("result :" + diff);
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
                ProductDTO productDTO=modelMapper.map(jpaRepo.save(checkProduct), ProductDTO.class);;
                return productDTO;
            }
        } else
            throw new InvalidProductException(errorList);
    }

    @Override
    @LoggerAnnotation
    public ProductDTO updateProduct(Product product, int oldProductId, String username) {
        if (jpaRepo.findById(oldProductId).isPresent()) {
            List<String> errorList = isProductValid(product);
            if (errorList.isEmpty()) {
                return modelMapper.map(jpaRepo.save(product),ProductDTO.class);
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
        if (jpaRepo.findById(id).isPresent()) {
            jpaRepo.deleteById(id);
            return true;
        } else throw new ResourceNotFoundException("Product not found. Id=" + id);
    }

    @Override
    @LoggerAnnotation
    public ProductDTO getProductById(int id, String username) {
        return modelMapper.map(jpaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. Id="+String.valueOf(id))),ProductDTO.class);
    }

    @Override
    @LoggerAnnotation
    public List<ProductDTO> getProductListInStock(String username) {
        return jpaRepo.findAllProductsByQuantity(0);
}

    @Override
    @LoggerAnnotation
    public int getTotalCountOfProduct(String username) {
        return jpaRepo.countByIsActiveTrue();
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
