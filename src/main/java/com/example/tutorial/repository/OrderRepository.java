package com.example.tutorial.repository;

import com.example.tutorial.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findOrderByIdAndCustomerId(Integer orderId, Integer customerId);
}
