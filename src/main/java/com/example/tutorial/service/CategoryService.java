package com.example.tutorial.service;

import com.example.tutorial.dto.Category.CategoryDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.CategoryRepository;
import com.example.tutorial.repository.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public Category getCategory(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> notFoundCategoryWithIdGiven(id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category create(CategoryDTO categoryDTO) {
        Category category = createCategoryFromCategoryDTO(categoryDTO);
        return categoryRepository.save(category);
    }

    public Category update(Integer id, CategoryDTO categoryDTO) {
        if (!categoryRepository.existsById(id)) {
            throw notFoundCategoryWithIdGiven(id);
        }
        Category category = createCategoryFromCategoryDTO(categoryDTO);
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Transactional(rollbackOn = { Exception.class, Throwable.class })
    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw notFoundCategoryWithIdGiven(id);
        }

        productCategoryRepository.deleteAllById_CategoryId(id);
        categoryRepository.deleteById(id);
    }

    private Category createCategoryFromCategoryDTO(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByNameIgnoreCase(categoryDTO.getName())) {
            throw new BusinessException(HttpStatus.CONFLICT.value(), "Category with name = " + categoryDTO.getName() + " existed");
        }
        return new Category(categoryDTO.getId(), categoryDTO.getName());
    }

    private RuntimeException notFoundCategoryWithIdGiven(Integer id) {
        return new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found category with id = " + id);
    }
}
