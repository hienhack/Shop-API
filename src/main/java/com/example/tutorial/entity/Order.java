package com.example.tutorial.entity;

import com.example.tutorial.enumeration.OrderState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 15)
    private OrderState state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    private boolean isCheckedOut;

    public Order() {
        this.state = OrderState.CONFIRMING;
        this.isCheckedOut = false;
    }

    public void updateState() {
        switch (this.state) {
            case CONFIRMING -> {
                this.state = OrderState.PACKING;
                return;
            }
            case PACKING -> {
                this.state = OrderState.SHIPPING;
                return;
            }
            case SHIPPING -> {
                this.state = OrderState.DELIVERING;
                this.delivered = LocalDateTime.now();
                return;
            }
            case DELIVERING -> {
                this.state = OrderState.FINISHED;
                this.shipped = LocalDateTime.now();
                return;
            }
            default -> {
                return;
            }
        }
    }
}
