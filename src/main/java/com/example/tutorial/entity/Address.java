package com.example.tutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"customer"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "province", length = 40)
    private String province;

    @Column(name = "district", length = 50)
    private String district;

    @Column(name = "ward", length = 50)
    private String ward;

    @Column(name = "village", length = 50)
    private String village;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.address);
        builder.append(", ");
        if (village != null && !village.isBlank()) {
            builder.append(this.village);
            builder.append(", ");
        }
        builder.append(this.ward);
        builder.append(", ");
        builder.append(this.district);
        builder.append(", ");
        builder.append(this.province);

        return builder.toString();
    }
}
