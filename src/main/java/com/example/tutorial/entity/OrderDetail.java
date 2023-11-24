package com.example.tutorial.entity;

import com.example.tutorial.enumeration.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
public class OrderDetail {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @Column(name = "type", length = 30)
    private String type;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "size", length = 4)
    private Size size;

    private float price;
    private int quantity;
    private float subTotal;

    public OrderDetail(Order order, Product product, String type, Size size, float price, int quantity) {
        this.order = order;
        this.product = product;
        this.type = type;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = price * quantity;
    }
}
