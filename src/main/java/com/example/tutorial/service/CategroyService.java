package com.example.tutorial.service;

import com.example.tutorial.entity.Category;
import com.example.tutorial.exception.ResourceNotFountException;
import com.example.tutorial.repository.CategoryRepository;
import com.example.tutorial.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategroyService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Category getCategory(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> notFoundCategoryWithIdGiven(id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Integer id, Category category) {
        if (categoryRepository.existsById(id)) {
            throw notFoundCategoryWithIdGiven(category.getId());
        }

        return categoryRepository.save(category);
    }

    public void delete(Integer id) {
        if (categoryRepository.existsById(id)) {
            throw notFoundCategoryWithIdGiven(id);
        }

        categoryRepository.deleteById(id);
    }

    public RuntimeException notFoundCategoryWithIdGiven(Integer id) {
        return new ResourceNotFountException("Not found category with id = " + id);
    }
}
