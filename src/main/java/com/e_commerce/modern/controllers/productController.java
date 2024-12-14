package com.e_commerce.modern.controllers;

import com.e_commerce.modern.exceptions.resourceNotFoundException;
import com.e_commerce.modern.model.category;
import com.e_commerce.modern.model.product;
import com.e_commerce.modern.requests.addProductRequest;
import com.e_commerce.modern.requests.productUpdateRequest;
import com.e_commerce.modern.response.apiResponse;
import com.e_commerce.modern.services.product.IproductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/product ")
@RequiredArgsConstructor
public class productController {
    private IproductService productService;

    @GetMapping("/all")
    public ResponseEntity<apiResponse> getAllProducts() {
        List<product> product = productService.getAllproduct();
        return ResponseEntity.ok(new apiResponse("found " , product));

    }
    @GetMapping("product/{productId}/product")
    public ResponseEntity<apiResponse> getProductById(@PathVariable Long productId) {
        try {
            product product = productService.getproductById(productId);
            return ResponseEntity.ok(new apiResponse("found " , product));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<apiResponse> addProduct(@RequestBody addProductRequest product) {
        try {
            product product1 =productService.addproduct(product);
            return ResponseEntity.ok(new apiResponse("added " , product1));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
        }

    }
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<apiResponse> updateProduct(@RequestBody productUpdateRequest product , @PathVariable Long productId) {
        try {
            product product1 =productService.updateproductById(product,productId);
            return ResponseEntity.ok(new apiResponse("updated " , product1));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(), null));
        }

    }
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<apiResponse> deleteProduct( @PathVariable Long productId) {
        try {
            productService.deleteproductById( productId);
            return ResponseEntity.ok(new apiResponse("deleted" , null));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(), null));
        }


    }
@GetMapping("/product/by/category-and-brand")
public ResponseEntity<apiResponse> findByCategoryAndBrand(@RequestParam category product,@RequestParam String brand){
    try {
        List<product> product1= productService.getproductByCategoryAndBrand(product,brand);
        if(product1.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse("not found",null));
        }
        return ResponseEntity.ok(new apiResponse("found" , product1));
    } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
    }

}
    @GetMapping("product/{brand}/byBrand")
    public ResponseEntity<apiResponse> getproductByBrand(@PathVariable String brand){

        try {
            List<product>  pro = productService.getproductByBrand(brand);
            if(pro.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse("not found",null));
            }
            return ResponseEntity.ok(new apiResponse("found" , pro));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
        }
    }

    //this method to find product by its category it return list of the category type
    @GetMapping("product/{category}/bycategory")
    public ResponseEntity<apiResponse> getproductBycategory(@PathVariable String category) {
        try {
            List<product> li =productService.getproductBycategory(category);
            if(li.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new apiResponse("not found",null));
            }
            return ResponseEntity.ok(new apiResponse("found" , li));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/product/{name}/byname")
public ResponseEntity<apiResponse> getproductByName(@PathVariable String name) {
    try {
        List<product> n =  productService.getproductByName(name);
        if(n.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse("not found",null));
        }
        return ResponseEntity.ok(new apiResponse("found" , n));
    } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
    }
}
@GetMapping("product/brand-and-name")
public ResponseEntity<apiResponse> getproductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
    try {
        List<product>  bn=productService.getproductByBrandAndName(brand,name);
        if(bn.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse("not found",null));
        }
        return ResponseEntity.ok(new apiResponse("found" , bn));
    } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
    }

}
@GetMapping("/product/countBy/brand-and-name")
public ResponseEntity<apiResponse> countByBrandAndName(@RequestParam String brand, @RequestParam String name) {
    try {
        var x=productService.countproductByBrandAndName(brand,name);
        return ResponseEntity.ok(new apiResponse("found" , x));
    } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
    }
}









}
