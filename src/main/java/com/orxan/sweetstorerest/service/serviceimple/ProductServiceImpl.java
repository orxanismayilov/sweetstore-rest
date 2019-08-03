package com.orxan.sweetstorerest.service.serviceimple;

import com.orxan.sweetstorerest.dtos.ProductsDTO;
import com.orxan.sweetstorerest.exceptions.InvalidProductException;
import com.orxan.sweetstorerest.exceptions.PermissionDeniedException;
import com.orxan.sweetstorerest.exceptions.ResourceNotFoundException;
import com.orxan.sweetstorerest.model.Product;
import com.orxan.sweetstorerest.repository.daoimpl.ProductDaoImpl;
import com.orxan.sweetstorerest.service.ProductService;
import com.orxan.sweetstorerest.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDaoImpl productDao;
    @Autowired
    private UserServiceImpl userService;

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
    public ProductsDTO getProductList(int pageIndex, int rowsPerPage,String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            int totalCount= productDao.getTotalCountOfProduct();
            int fromIndex=pageIndex*rowsPerPage;
            int toIndex=Math.min(fromIndex+rowsPerPage,totalCount);
            ProductsDTO productsDTO=new ProductsDTO();
            List<Product> productList=productDao.getProductList(fromIndex,toIndex);
            if (!productList.isEmpty()) {
                productsDTO.setProducts(productList);
                productsDTO.setCount(productDao.getTotalCountOfProduct());
            } else throw new ResourceNotFoundException("No products found.");
            return productsDTO;
        } else throw new PermissionDeniedException("You don't have permission for this action.");
    }

    @Override
    public Product addProduct(Product product,String username) {
       if(userService.getUserRole(username).getCode()>=1) {
           List<String> errorList= isProductValid(product);
           if (errorList.isEmpty()) {
               Product checkProduct=productDao.checkProductNameIsExist(product.getName());
               if (checkProduct==null) {
                   return productDao.addProduct(product);
               } else {
                   checkProduct.setQuantity(product.getQuantity()+checkProduct.getQuantity());
                   checkProduct.setPrice(product.getPrice());
                   productDao.updateProduct(checkProduct,checkProduct.getId());
               }
           } else
               throw new InvalidProductException(errorList);
           return null;
       } else throw new PermissionDeniedException("You don't have permission for this action.");
    }

    @Override
    public Product updateProduct(Product product, int oldProductId,String username) {
        if (userService.getUserRole(username).getCode()>=1) {
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
        } else throw new PermissionDeniedException("You don't have permission for this action.");
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
    public boolean deleteProductByID(int id,String username) {
        if (userService.getUserRole(username).getCode()>1) {
            boolean exist = productDao.isProductExist(id);
            if (exist) {
                productDao.deleteProductById(id);
                return true;
            } else throw new ResourceNotFoundException("Product not found. Id=" + id);
        }
        throw new PermissionDeniedException("You don't have permission for this action.");
    }

    @Override
    public Product getProductById(int id,String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            Product product = productDao.getProductById(id);
            if (product != null) {
                return product;
            } else throw new ResourceNotFoundException("Product not found. id=" + id);
        }
        throw new PermissionDeniedException("You don't have permission for this action.");
    }

    @Override
    public List<Product> getProductListInStock(String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            return productDao.getProductListForComboBox();
        }
        throw new PermissionDeniedException("You don't have permission for this action.");
    }

    @Override
    public int getTotalCountOfProduct(String username) {
        if (userService.getUserRole(username).getCode()>=1) {
            return productDao.getTotalCountOfProduct();
        }
        throw new PermissionDeniedException("You don't have permission for this action.");
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
