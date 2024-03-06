package com.example.tutorial.dto.Cart;

import com.example.tutorial.dto.CartItem.CartItemDTO;
import com.example.tutorial.entity.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    private Integer cartId;
    private Integer userId;

    @JsonProperty("cart_items")
    private List<CartItemDTO> cartItems;

    public CartDTO(Cart entity) {
        this.cartId = entity.getId();
        this.userId = entity.getCustomer().getId();
        this.cartItems = entity.getItems().stream().map(CartItemDTO::new).toList();
    }
}
