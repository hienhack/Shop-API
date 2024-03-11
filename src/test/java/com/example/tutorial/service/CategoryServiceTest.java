package com.example.tutorial.service;


import com.example.tutorial.entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void createCategory() {
//        Category c1 = new Category(null, "T Shirt");
//        categoryService.create(c1);
    }

    @Test
    void getAllCategory() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            System.out.println(ow.writeValueAsString(category));
        }
    }
}
