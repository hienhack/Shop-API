package com.example.tutorial.controller;

import com.example.tutorial.dto.Category.CategoryDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.service.CategoryService;
import com.example.tutorial.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<List<Category>> getAllCategories() {
        return ResponseDTO.of(categoryService.getAllCategories());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<Category> create(@RequestBody @Valid CategoryDTO category) {
        return ResponseDTO.of(categoryService.create(category));
    }

    @PutMapping (value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<Category> update(@PathVariable Integer id, @RequestBody @Valid CategoryDTO category) {
        return ResponseDTO.of(categoryService.update(id, category));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        categoryService.delete(id);
    }
}
