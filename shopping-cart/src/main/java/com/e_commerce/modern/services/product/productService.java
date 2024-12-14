package com.e_commerce.modern.services.product;

import com.e_commerce.modern.exceptions.resourceNotFoundException;
import com.e_commerce.modern.model.category;
import com.e_commerce.modern.model.product;
import com.e_commerce.modern.exceptions.productNotFoundException;
import com.e_commerce.modern.requests.addProductRequest;
import com.e_commerce.modern.requests.productUpdateRequest;
import com.e_commerce.modern.services.repository.ProductRepository;
import com.e_commerce.modern.services.repository.categoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class productService implements IproductService{
    private final ProductRepository productRepository;
    categoryRepository categoryRepository;

    // this method is to add product service  method #1
    @Override
    public product addproduct(addProductRequest product) {

        category category =  Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(()->{
                    category newCategory = new category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        return productRepository.save(createProduct(product , category));
    }



    // this method is to update product method #2
    @Override
    public product  updateproductById(productUpdateRequest request ,Long productId){
    return productRepository.findById(productId).map(existingProduct ->updateExistingproduct(existingProduct ,request))
            .map(productRepository::save)
            .orElseThrow(()-> new resourceNotFoundException("product not found! "));
    }
    private product updateExistingproduct(product existingProduct, productUpdateRequest productUpdateRequest) {
        existingProduct.setName(productUpdateRequest.getName());
        existingProduct.setPrice(productUpdateRequest.getPrice());
        existingProduct.setBrand(productUpdateRequest.getBarnd());
        existingProduct.setInventory(productUpdateRequest.getInventory());
        existingProduct.setDescription(productUpdateRequest.getDescription());
        category category =categoryRepository.findByName(productUpdateRequest.getCategory().getName());
        existingProduct.setName(category.getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }




    private product createProduct(addProductRequest product , category category) {
        return new product(
                product.getName(),
                product.getPrice(),
                product.getBarnd(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    @Override
    public product getproductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new resourceNotFoundException("product not found!"));
    }



    @Override
    public void deleteproductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete , ()->{throw new resourceNotFoundException("product not found!");});

    }

    @Override
    public List<product> getAllproduct() {
        return productRepository.findAll();
    }

    @Override
    public List<product> getproductBycategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<product> getproductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<product> getproductByCategoryAndBrand(category category, String brand) {
        return productRepository.findByCategoryAndBrand(category,brand);
    }

    @Override
    public List<product> getproductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<product> getproductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countproductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
