package com.example.tutorial.dto.CartItem;

import lombok.Data;

@Data
public class CartItemCreationDTO {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer typeId;
    private Integer sizeId;
    private int quantity;
}
