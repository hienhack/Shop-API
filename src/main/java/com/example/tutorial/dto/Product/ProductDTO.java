package com.example.tutorial.dto.Product;

import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.dto.Size.SizeDTO;
import com.example.tutorial.dto.Type.TypeDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.entity.Product;
import com.example.tutorial.entity.ProductImage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String thumbnail;

    @NotEmpty
    private String description;

    @NotNull
    private Float price;

    private List<String> images;

    @NotEmpty
    private @Valid List<TypeDTO> types;

    @NotEmpty
    private @Valid List<SizeDTO> sizes;

    @NotEmpty
    private List<StockDetailDTO> stock;

    @NotEmpty
    private List<Category> categories;

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumbnail = entity.getThumbnail();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
        this.categories = entity.getCategories();
        this.images = entity.getImages().stream().map(ProductImage::getUrl).collect(Collectors.toList());
        this.types = entity.getTypes().stream().map(TypeDTO::new).collect(Collectors.toList());
        this.sizes = entity.getSizes().stream().map(SizeDTO::new).collect(Collectors.toList());
        this.stock = entity.getStock().stream().map(StockDetailDTO::new).collect(Collectors.toList());
    }
}
