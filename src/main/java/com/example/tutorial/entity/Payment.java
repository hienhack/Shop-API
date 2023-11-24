package com.example.tutorial.entity;

import com.example.tutorial.enumeration.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paid_date")
    private LocalDateTime paid;

    @Column(name = "expired_date")
    private LocalDateTime expired;

    private long momoTransId;
    private Boolean isPaid;
    private int total;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", length = 15)
    private PaymentMethod method;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    public Payment(int total, PaymentMethod method, User customer) {
        this.expired = LocalDateTime.now().plusHours(24);
        this.isPaid = false;
        this.total = total;
        this.method = method;
        this.customer = customer;
    }
}
