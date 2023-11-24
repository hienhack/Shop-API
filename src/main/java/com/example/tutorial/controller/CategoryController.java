package com.example.tutorial.controller;

import com.example.tutorial.entity.Category;
import com.example.tutorial.service.CategroyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategroyService categroyService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categroyService.getAllCategories();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categroyService.create(category);
    }

    @PutMapping (value = "/{id}")
    public Category update(@PathVariable Integer id, @RequestBody Category category) {
        return categroyService.update(id, category);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        categroyService.delete(id);
    }
}
