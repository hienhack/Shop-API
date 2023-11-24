package com.example.tutorial.controller;

import com.example.tutorial.dto.CartItem.CartItemCreationDTO;
import com.example.tutorial.dto.CartItem.CartItemDTO;
import com.example.tutorial.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;

    @PostMapping
    public CartItemDTO addItem(@RequestBody CartItemCreationDTO cartItemDTO) {
        return cartItemService.create(cartItemDTO);
    }

    @PutMapping(value = "/{itemId}")
    public CartItemDTO updateItem(
            @PathVariable(name = "itemId", required = true) Integer itemId,
            @RequestBody CartItemCreationDTO cartItemDTO
            ) {
        return cartItemService.update(itemId, cartItemDTO);
    }

    @DeleteMapping(value = "/{itemId}")
    public void deleteItem(@PathVariable(name = "itemId", required = true) Integer itemId) {
        cartItemService.delete(itemId);
    }
}
