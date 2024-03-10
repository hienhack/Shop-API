package com.example.tutorial.entity;

import com.example.tutorial.enumeration.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paid_date")
    private LocalDateTime paid;

    private Boolean isPaid;
    private int total;

    @Column(name = "momo_trans_id")
    private long momoTransId;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", length = 15)
    private PaymentMethod method;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    public Payment(int cost, PaymentMethod method, User customer) {
        this.total = cost;
        this.method = method;
        this.customer = customer;
        this.isPaid = method.equals(PaymentMethod.COD);
    }
}
