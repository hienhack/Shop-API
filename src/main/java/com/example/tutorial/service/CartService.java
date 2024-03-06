package com.example.tutorial.service;

import com.example.tutorial.dto.Cart.CartDTO;
import com.example.tutorial.dto.CartItem.CartItemCreationDTO;
import com.example.tutorial.dto.CartItem.CartItemDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.CartItemRepository;
import com.example.tutorial.repository.CartRepository;
import com.example.tutorial.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartDTO getCart(User customer) {
        Cart cart = cartRepository.findByCustomer_Id(customer.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "Cart not found"));

        return new CartDTO(cart);
    }

    public CartDTO create(User customer) {
        if (cartRepository.existsByCustomerId(customer.getId()))
            throw new RuntimeException("Cart existed already");

        Cart cart = new Cart();
        cart.setCustomer(customer);

        return new CartDTO(cartRepository.save(cart));
    }

    public CartItemDTO addItem(Integer userId, CartItemCreationDTO item) {
        Cart cart = cartRepository.findByCustomer_Id(userId)
                .orElseThrow(() -> new BusinessException("Cart not found"));
        CartItem cartItem = createCartItemFromCartItemDTO(cart, item);
        return new CartItemDTO(cartItemRepository.save(cartItem));
    }

    public CartItemDTO updateItem(Integer userId, Integer itemId, CartItemCreationDTO item) {
        Cart cart = cartRepository.findByCustomer_Id(userId)
                .orElseThrow(() -> new BusinessException("Cart not found"));

        if (!cartItemRepository.existsByIdAndCart_Id(itemId, cart.getId())) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found item with id = " + itemId);
        }
        CartItem cartItem = createCartItemFromCartItemDTO(cart, item);
        cartItem.setId(itemId);
        return new CartItemDTO(cartItemRepository.save(cartItem));
    }

    public void deleteItem(Integer userId, Integer itemId) {
        Cart cart = cartRepository.findByCustomer_Id(userId).orElseThrow(() -> new BusinessException("Cart not found"));
        if (!cartItemRepository.existsByIdAndCart_Id(itemId, cart.getId())) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found item with id = " + itemId);
        }

        cartItemRepository.deleteById(itemId);
    }

    private CartItem createCartItemFromCartItemDTO(Cart cart, CartItemCreationDTO item) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found product with id = " + item.getProductId()));

        Type type = product.getTypes()
                .stream().filter(t -> t.getId().equals(item.getTypeId())).findFirst()
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found type with id = " + item.getTypeId()));

        ProductSize size = product.getSizes()
                .stream().filter(s -> s.getId().equals(item.getSizeId())).findFirst()
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found size with id = " + item.getSizeId()));

        StockDetail stockDetail = product.getStock().stream()
                .filter(sd -> sd.equals(new StockDetail(null, null, type, size, 0)))
                .findFirst().orElse(null);
        int inStock = stockDetail != null ? stockDetail.getInStock() : 0;

        if (inStock < item.getQuantity()) {
            throw new BusinessException(HttpStatus.FORBIDDEN.value(), "Not enough items");
        }

        return new CartItem(null, cart, product, type, size, item.getQuantity(), LocalDateTime.now());
    }
}
