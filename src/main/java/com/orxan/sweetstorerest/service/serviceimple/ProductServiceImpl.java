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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepo jpaRepo;
    private final ModelMapper modelMapper;

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

    @Autowired
    public ProductServiceImpl(ProductJpaRepo jpaRepo, ModelMapper modelMapper) {
        this.jpaRepo = jpaRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public ProductsDTO getProductList(int pageIndex, int rowsPerPage) {
        long startTime = System.currentTimeMillis();
        long usertime = System.currentTimeMillis();
        long diff = usertime - startTime;
        //logger.info("time difference :" + diff);
        int totalCount = getTotalCountOfProduct();
        long time = System.currentTimeMillis();
        diff = time - usertime;
        //logger.info("total count diff :" + diff);
        ProductsDTO productsDTO = new ProductsDTO();
        long prList = System.currentTimeMillis();
        diff = prList - time;
        //logger.info("time difference prList :" + diff);
        productsDTO.setProducts(jpaRepo.findByIsActiveTrue(createPageRequest(pageIndex, rowsPerPage)));
        productsDTO.setCount(totalCount);
        long finalTime = System.currentTimeMillis();
        diff = finalTime - startTime;
        //logger.info("result :" + diff);
        return productsDTO;
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public ProductDTO addProduct(Product product) {
        List<String> errorList = isProductValid(product);
        if (errorList.isEmpty()) {
            Product checkProduct = jpaRepo.findFirstByName(product.getName());
            if (checkProduct == null) {
                return modelMapper.map(jpaRepo.save(product), ProductDTO.class);
            } else {
                checkProduct.setQuantity(product.getQuantity() + checkProduct.getQuantity());
                checkProduct.setPrice(product.getPrice());
                ProductDTO productDTO = modelMapper.map(jpaRepo.save(checkProduct), ProductDTO.class);
                ;
                return productDTO;
            }
        } else
            throw new InvalidProductException(errorList);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public ProductDTO updateProduct(Product product, int oldProductId) {
        if (jpaRepo.findById(oldProductId).isPresent()) {
            List<String> errorList = isProductValid(product);
            if (errorList.isEmpty()) {
                product.setId(oldProductId);
                return modelMapper.map(jpaRepo.save(product), ProductDTO.class);
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
    @Secured({"ROLE_ADMIN"})
    public void deleteProductByID(int id) {
        if (jpaRepo.findById(id).isPresent()) {
            jpaRepo.deleteById(id);
        } else throw new ResourceNotFoundException("Product not found. Id=" + id);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public ProductDTO getProductById(int id) {
        return modelMapper.map(jpaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. Id=" + String.valueOf(id))), ProductDTO.class);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public List<ProductDTO> getProductListInStock() {
        return jpaRepo.findAllProductsByQuantity(0);
    }

    @Override
    @LoggerAnnotation
    @Secured({"ROLE_ADMIN,ROLE_USER"})
    public int getTotalCountOfProduct() {
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

    private Pageable createPageRequest(int page, int rows) {
        return PageRequest.of(page, rows, Sort.Direction.DESC, "id");
    }
}
