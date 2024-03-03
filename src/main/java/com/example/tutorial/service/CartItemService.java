package com.example.tutorial.service;

import com.example.tutorial.dto.CartItem.CartItemCreationDTO;
import com.example.tutorial.dto.CartItem.CartItemDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.CartItemRepository;
import com.example.tutorial.repository.CartRepository;
import com.example.tutorial.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartItemDTO create(CartItemCreationDTO item) {
        Cart cart = cartRepository.findById(item.getCartId())
                .orElseThrow(() -> new BusinessException("Not found cart with id = " + item.getCartId()));

        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new BusinessException("Not found product with id = " + item.getProductId()));

        Type type = product.getTypes()
                .stream().filter(t -> t.getId().equals(item.getTypeId())).findFirst()
                .orElseThrow(() -> new BusinessException("Not found type with id = " + item.getTypeId()));

        ProductSize size = product.getSizes()
                .stream().filter(s -> s.getId().equals(item.getSizeId())).findFirst()
                .orElseThrow(() -> new BusinessException("Not found size with id = " + item.getSizeId()));

        StockDetail stockDetail = product.getStock().stream()
                .filter(sd -> sd.equals(new StockDetail(null, null, type, size, 0)))
                .findFirst().orElse(null);
        int inStock = stockDetail != null ? stockDetail.getInStock() : 0;

        if (inStock < item.getQuantity()) {
            throw new BusinessException("Not enough item");
        }

        CartItem cartItem = new CartItem(item.getId(), cart, product, type, size, item.getQuantity());
        return new CartItemDTO(cartItemRepository.save(cartItem));
    }

    public CartItemDTO update(Integer cartItemId, CartItemCreationDTO cartItem) {
        if (!cartItemRepository.existsById(cartItemId))
            throw new BusinessException("Not found cart item with id = " + cartItemId);

        return create(cartItem);
    }

    public void delete(Integer cartItemId) {
        if (!cartItemRepository.existsById(cartItemId))
            throw new BusinessException("Not found cart item with id = " + cartItemId);

        cartItemRepository.deleteById(cartItemId);
    }
}
