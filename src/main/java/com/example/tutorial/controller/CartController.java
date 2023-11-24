package com.example.tutorial.controller;

import com.example.tutorial.dto.Cart.CartDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public CartDTO createCart(Principal principal) {
        User customer = (User)principal;
        return cartService.create(customer);
    }

    @GetMapping(value = "/{cartId}")
    public CartDTO getCart(@PathVariable(name = "cartId", required = true) Integer cartId) {
        return cartService.getCart(cartId);
    }
}
