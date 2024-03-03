package com.example.tutorial.service;

import com.example.tutorial.dto.Cart.CartDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.CartRepository;
import com.example.tutorial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartDTO getCart(Integer cartId) {
        return cartRepository.findById(cartId).map(CartDTO::new)
                .orElseThrow(() -> new BusinessException("Not found cart with id = " + cartId));
    }

    public CartDTO create(User customer) {
        if (cartRepository.existsByCustomerId(customer.getId()))
            throw new RuntimeException("Cart existed already");

        Cart cart = new Cart();
        cart.setCustomer(customer);

        return new CartDTO(cartRepository.save(cart));
    }
}
