package com.example.tutorial.repository;

import com.example.tutorial.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public boolean existsByNameIgnoreCase(String name);
    public Optional<Category> findCategoryByNameIgnoreCase(String name);
}