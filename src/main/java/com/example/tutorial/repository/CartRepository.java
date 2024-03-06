package com.example.tutorial.repository;

import com.example.tutorial.entity.Cart;
import com.example.tutorial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public boolean existsByCustomerId(Integer id);
    public Optional<Cart> findByCustomer_Id(Integer id);
}
