package com.e_commerce.modern.services.repository;

import com.e_commerce.modern.model.category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;

public interface categoryRepository extends JpaRepository<category, Long> {

    category findByName(String name);

    boolean existsByName(String name);
}
