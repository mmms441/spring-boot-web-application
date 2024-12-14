package com.e_commerce.modern.services.product;

import com.e_commerce.modern.model.category;
import com.e_commerce.modern.model.product;
import com.e_commerce.modern.requests.addProductRequest;
import com.e_commerce.modern.requests.productUpdateRequest;

import java.util.List;

public interface IproductService {
    product addproduct(addProductRequest product);

    product getproductById(Long id);
    product updateproductById(productUpdateRequest product , Long productId);
    void deleteproductById(Long id);
    List<product> getAllproduct();
    List<product> getproductBycategory(String category);
    List<product> getproductByBrand(String brand);
    List<product> getproductByCategoryAndBrand(category category, String brand);
    List<product> getproductByName(String productName);
    List<product> getproductByBrandAndName(String brand, String productName);
   Long countproductByBrandAndName(String brand, String productName);
}
