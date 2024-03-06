package com.example.tutorial.dto.CartItem;

import com.example.tutorial.dto.Product.ProductInCartDTO;
import com.example.tutorial.entity.CartItem;
import com.example.tutorial.enumeration.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartItemDTO {
    private Integer id;
    private ProductInCartDTO product;
    private String type;
    private Size size;
    private int quantity;

    @JsonProperty("added_time")
    private LocalDateTime addedTime;

    @JsonProperty("in_stock")
    private int inStock;

    public CartItemDTO(CartItem entity) {
        this.id = entity.getId();
        this.product = new ProductInCartDTO(entity.getProduct());
        this.type = entity.getType().getName();
        this.size = entity.getSize().getSize();
        this.quantity = entity.getQuantity();
        this.addedTime = entity.getAddedTime();
        this.inStock = entity.getProduct().getStock().stream().filter(s ->
                s.getType().getName().equals(type) && s.getSize().getSize().equals(size)).findFirst().get().getInStock();
    }
}
