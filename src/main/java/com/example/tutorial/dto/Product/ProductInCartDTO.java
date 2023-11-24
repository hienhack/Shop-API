package com.example.tutorial.dto.Product;

import com.example.tutorial.dto.Size.SizeDTO;
import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.dto.Type.TypeDTO;
import com.example.tutorial.entity.Product;
import com.example.tutorial.entity.StockDetail;
import com.example.tutorial.enumeration.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCartDTO {
    private Integer id;
    private String name;
    private String thumb;
    private List<TypeDTO> types;
    private List<SizeDTO> sizes;
    private List<StockDetailDTO> stock;

    public ProductInCartDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumb = entity.getThumbnail();
        this.types = entity.getTypes().stream().map(TypeDTO::new).toList();
        this.sizes = entity.getSizes().stream().map(SizeDTO::new).toList();
        this.stock = entity.getStock().stream().map(StockDetailDTO::new).toList();
    }
}
