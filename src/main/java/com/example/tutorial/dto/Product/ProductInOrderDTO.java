package com.example.tutorial.dto.Product;

import com.example.tutorial.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductInOrderDTO {
    private Integer id;
    private String name;
    private String thumb;

    public ProductInOrderDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumb = entity.getThumbnail();
    }
}
