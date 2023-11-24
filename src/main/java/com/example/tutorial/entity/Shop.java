package com.example.tutorial.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shop")
@Data
public class Shop {
    @Id
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "phone", length = 11)
    private String phone;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
