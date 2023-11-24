package com.example.tutorial.repository;

import com.example.tutorial.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //    public Page<Product> findAllProduct(Integer categoryId, Pageable pageRequest);
    public boolean existsByNameIgnoreCase(String name);
}