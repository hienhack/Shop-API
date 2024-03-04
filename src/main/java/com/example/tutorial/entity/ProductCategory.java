package com.example.tutorial.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "product_category")
public class ProductCategory {
    @EmbeddedId
    private ProductCategoryId id;

    @Embeddable
    public static class ProductCategoryId implements Serializable {
        @Column(name = "product_id")
        private Integer productId;

        @Column(name = "category_id")
        private Integer categoryId;
    }
}
