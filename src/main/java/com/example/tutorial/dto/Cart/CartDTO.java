package com.example.tutorial.dto.Cart;

import com.example.tutorial.dto.CartItem.CartItemDTO;
import com.example.tutorial.entity.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    private Integer cartId;
    private Integer userId;
    private List<CartItemDTO> cartItems;

    public CartDTO(Cart entity) {
        this.cartId = entity.getId();
        this.userId = entity.getCustomer().getId();
        this.cartItems = entity.getItems().stream().map(CartItemDTO::new).toList();
    }
}
