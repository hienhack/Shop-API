package com.example.tutorial.controller;

import com.example.tutorial.dto.Cart.CartDTO;
import com.example.tutorial.dto.CartItem.CartItemCreationDTO;
import com.example.tutorial.dto.CartItem.CartItemDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.service.CartService;
import com.example.tutorial.util.AuthenticationHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseDTO<CartDTO> createCart(Authentication authentication) {
        var customer = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(cartService.create(customer));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<CartDTO> getCart(Authentication authentication) {
        var customer = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(cartService.getCart(customer));
    }


    @PostMapping("/items")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<CartItemDTO> addItem(Authentication authentication, @RequestBody @Valid CartItemCreationDTO item) {
        var customer = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(cartService.addItem(customer.getId(), item));
    }

    @PutMapping("/items/{itemId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<CartItemDTO> updateItem(
            Authentication authentication,
            @PathVariable Integer itemId,
            @RequestBody @Valid CartItemCreationDTO item
    ) {
        var customer = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(cartService.updateItem(customer.getId(), itemId, item));
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItem(Authentication authentication, @PathVariable(name = "itemId") Integer itemId) {
        var customer = AuthenticationHelper.getUser(authentication);
        cartService.deleteItem(customer.getId(), itemId);
    }
}
