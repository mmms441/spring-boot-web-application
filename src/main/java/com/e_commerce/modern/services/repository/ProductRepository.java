package com.e_commerce.modern.services.repository;

import com.e_commerce.modern.model.category;
import com.e_commerce.modern.model.product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<product, Long> {
    List<product> findByCategoryName(String category);

    List<product> findByBrand(String brand);

    List<product> findByCategoryAndBrand(category category, String brand);

    List<product> findByBrandAndName(String brand, String productName);

    List<product> findByName(String productName);

    Long countByBrandAndName(String brand, String name);
}
