package com.example.tutorial.entity;

import com.example.tutorial.enumeration.DeliveryProviderName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address", length = 350)
    private String address;

    @Column(name = "receiver_name", length = 40)
    private String name;

    @Column(name = "receiver_phone", length = 10)
    private String phone;

    private float weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_name", length = 50)
    private DeliveryProviderName serviceName;

    private int fee;

    public Delivery(String address, String name, String phone, float weight, DeliveryProviderName serviceName, int fee) {
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.weight = weight;
        this.fee = fee;
        this.serviceName = serviceName;
    }
}
