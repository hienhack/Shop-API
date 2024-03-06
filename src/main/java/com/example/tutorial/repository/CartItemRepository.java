package com.example.tutorial.repository;

import com.example.tutorial.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public boolean existsByIdAndCart_Id(Integer itemId, Integer cartId);

    @Query(value = "SELECT * FROM cart_item ci WHERE ci.cartId = ?1 AND ci.productId = ?2 AND ci.typeId = ?3 AND ci.sizeId = ?4", nativeQuery = true)
    public Optional<CartItem> findItem(Cart cart, Product product, Type type, ProductSize size);
}
