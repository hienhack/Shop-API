package com.example.tutorial.dto.Product;

import com.example.tutorial.entity.Product;
import lombok.Data;

@Data
public class ProductInListDTO {
    private Integer id;
    private String name;
    private String thumbnail;
    private Float price;

    public ProductInListDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumbnail = entity.getThumbnail();
        this.price = entity.getPrice();
    }
}
