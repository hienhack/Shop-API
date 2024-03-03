package com.example.tutorial.repository;

import com.example.tutorial.entity.Category;
import com.example.tutorial.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Page<Product> findAll(Pageable pageInfo);
    public Page<Product> findProductByNameContainingIgnoreCase(String name, Pageable pageInfo);

    public Page<Product> findProductByCategoriesContains(Category categories, Pageable pageable);

    @Query(value = "SELECT p.name FROM product p WHERE p.name LIKE %:keyword% LIMIT :maxResults", nativeQuery = true)
    public List<String> findRelatedProductByKeywords(@Param("keyword") String keyword, Integer maxResults);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    public Page<Product> findRelatedProductByKeywords(@Param("keyword") String keyword, Pageable pageable);
}
