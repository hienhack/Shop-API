package com.example.tutorial.entity;

import com.example.tutorial.enumeration.OrderState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 15)
    private OrderState state;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @Column(name = "created_date")
    private LocalDateTime created;

    @Column(name = "delivered_date")
    private LocalDateTime delivered;

    @Column(name = "shipped_date")
    private LocalDateTime shipped;

    @Column(name = "cancelled_date")
    private LocalDateTime cancelled;

    private int productsCost;
    private int totalCost;

    public void setState(OrderState state) {
        switch (state) {
            case SHIPPING:
                this.shipped = LocalDateTime.now();
                break;
            case DELIVERING:
                this.delivered
        }
    }
}
