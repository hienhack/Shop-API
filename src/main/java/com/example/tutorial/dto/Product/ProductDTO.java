package com.example.tutorial.dto.Product;

import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.dto.Size.SizeDTO;
import com.example.tutorial.dto.Type.TypeDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.entity.Product;
import com.example.tutorial.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String thumbnail;
    private String description;
    private Float price;
    private List<String> images;
    private List<TypeDTO> types;
    private List<SizeDTO> sizes;
    private List<StockDetailDTO> stock;
    private List<Category> categories;

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumbnail = entity.getThumbnail();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
        this.categories = entity.getCategories();
        this.images = entity.getImages().stream().map(ProductImage::getUrl).toList();
        this.types = entity.getTypes().stream().map(TypeDTO::new).toList();
        this.sizes = entity.getSizes().stream().map(SizeDTO::new).toList();
        this.stock = entity.getStock().stream().map(StockDetailDTO::new).toList();
    }
}
