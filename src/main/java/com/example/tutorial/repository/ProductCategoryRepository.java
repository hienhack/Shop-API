package com.example.tutorial.repository;

import com.example.tutorial.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategory.ProductCategoryId> {
    public void deleteAllById_CategoryId(Integer id);
}
