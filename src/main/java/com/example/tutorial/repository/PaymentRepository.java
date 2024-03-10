package com.example.tutorial.repository;

import com.example.tutorial.entity.Payment;
import com.example.tutorial.enumeration.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    public Optional<Payment> findByIdAndMethodAndCustomer_Id(Integer paymentId, PaymentMethod method, Integer userId);
}
