package com.example.tutorial.repository;

import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findOrderByIdAndCustomer_Id(Integer orderId, Integer customerId);
    public Page<Order> findByCreatedBetween(LocalDateTime from, LocalDateTime to, Pageable pageRequest);
    public Page<Order> findByStateIsInAndCustomer_Id(List<OrderState> states, Integer customerId, Pageable pageRequest);
    public Page<Order> findByStateIsIn(List<OrderState> states, Pageable pageRequest);
}
