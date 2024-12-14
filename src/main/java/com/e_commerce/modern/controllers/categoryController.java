package com.e_commerce.modern.controllers;

import com.e_commerce.modern.exceptions.alreadyExistException;
import com.e_commerce.modern.exceptions.resourceNotFoundException;
import com.e_commerce.modern.model.category;
import com.e_commerce.modern.response.apiResponse;
import com.e_commerce.modern.services.category.IcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class categoryController {
    private final IcategoryService icategoryService;
    //this method is use built in class ResponseEntity to handle http request
    // that in our example use to retrieve all items in the category list ,
    // we first initialize list to retrieve all the category in the repository class
    // and then we check if found we will retrieve this list and if not found
    // we will handle this error usin Response entity and we make the type of response is
    // apiResponse that contain message (String) and object of any tybe
    @GetMapping("/all")
    public ResponseEntity<apiResponse> getAllCategories() {
        try {
            List<category> categories= icategoryService.findAllCategories();
            return ResponseEntity.ok(new apiResponse("found ", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse("RESOURCE NOT FOUND" , INTERNAL_SERVER_ERROR));
        }

    }
    //this method is post type method we invoded a function interface that direct interact with the repository to search
    // for an category by its id and if find it ,it will save its reference in the variable category and then logic is if
    // he found the category he will hanle response by ResponseEntity and give it a new object from class apiResponse with
    // message and the saved category object and if not found and exception contain ResponseEntity with conflict and
    // error.message in the calss exception we created before and pass null in the object in the new apiResponse object
    @PostMapping("/addcategory")
    public ResponseEntity<apiResponse> addCategory(@RequestBody category category) {
        try {
           category addedcategory= icategoryService.addCategory(category);
           return ResponseEntity.ok(new apiResponse("added ", addedcategory));
        } catch (alreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new apiResponse("not added ", null));




    }

    }
    //method to return category by id , same nearly for the above methods
    @GetMapping("/getById/{categoryId}/category")
    public ResponseEntity<apiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            category category= icategoryService.findcategoryById(categoryId);
            return ResponseEntity.ok(new apiResponse("found ", category));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse( e.getMessage(), null));
        }


    }
    //method to find the category by name , same logic like above methods with small changes
    @GetMapping("/{name}/category")
    public ResponseEntity<apiResponse> getCategoryByName(@PathVariable String name){
        try {
            category category= icategoryService.findByName(name);
            return ResponseEntity.ok(new apiResponse("found ", category));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse( e.getMessage(), null));
        }


    }
    //delete category by its id , same logic like above methods
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<apiResponse> deleteCategory(@PathVariable Long id ){
        try {
             icategoryService.deleteCategory(id);
            return ResponseEntity.ok(new apiResponse("deleted ", null));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse( e.getMessage(), null));
        }


    }

    @PutMapping("/category/{id}/delete")
    public ResponseEntity<apiResponse> updateCategory(@PathVariable Long id ,@RequestBody category category){
        try {
            category category1= icategoryService.updateCategory(category , id);
            return ResponseEntity.ok(new apiResponse("updated ", category1));
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse( e.getMessage(), null));
        }


    }








}
