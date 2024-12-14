package com.e_commerce.modern.services.category;

import com.e_commerce.modern.model.category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IcategoryService {
    public category findByName(String name);
    public category findcategoryById(long id);
    List<category> findAllCategories();
    public category addCategory(category category);
    public category updateCategory(category category , Long id);
    public void deleteCategory(Long id);

}
