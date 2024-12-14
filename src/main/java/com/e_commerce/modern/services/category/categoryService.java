package com.e_commerce.modern.services.category;

import com.e_commerce.modern.exceptions.alreadyExistException;
import com.e_commerce.modern.model.category;
import com.e_commerce.modern.exceptions.resourceNotFoundException;
import com.e_commerce.modern.services.repository.categoryRepository;
 import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class categoryService implements IcategoryService{
    private final categoryRepository repo;
    @Override
    public category findByName(String name) {
    return repo.findByName(name);

    }

    @Override
    public category findcategoryById(long id) {
        return repo.findById(id).orElseThrow(()-> new resourceNotFoundException("resource not found!"));
    }

    @Override
    public List<category> findAllCategories() {
        return repo.findAll();
    }

    @Override
    public category addCategory(category category) {
        return Optional.of(category).filter(c -> !repo.existsByName(c.getName()))
                .map(repo :: save)
                .orElseThrow(() -> new alreadyExistException("resource not found!"));
    }


    @Override
    public category updateCategory(category category ,Long id) {
        return Optional.ofNullable(findcategoryById(id)).map(oldCategory->{
                oldCategory.setName(category.getName());
                return repo.save(oldCategory);
                }).orElseThrow(()-> new resourceNotFoundException("resource not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        repo.findById(id).ifPresentOrElse(repo ::delete, ()-> {
            throw new resourceNotFoundException("resource not found!");
        });

    }
}
