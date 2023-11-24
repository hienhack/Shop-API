package com.example.tutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_images")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id
    @Column(name = "url", length = 100)
    private String url;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
