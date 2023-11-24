package com.example.tutorial.dto.CartItem;

import com.example.tutorial.dto.Product.ProductInCartDTO;
import com.example.tutorial.entity.CartItem;
import com.example.tutorial.enumeration.Size;
import lombok.Data;

@Data
public class CartItemDTO {
    private Integer id;
    private ProductInCartDTO product;
    private String type;
    private Size size;
    private int quantity;

    public CartItemDTO(CartItem entity) {
        this.id = entity.getId();
        this.product = new ProductInCartDTO(entity.getProduct());
        this.type = entity.getType().getName();
        this.size = entity.getSize().getSize();
    }
}
