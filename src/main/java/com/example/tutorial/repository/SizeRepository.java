package com.example.tutorial.repository;

import com.example.tutorial.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<ProductSize, Integer> {
}
