package com.example.tutorial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "image", length = 100)
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // For removing only
    @OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE)
    private List<StockDetail> stockDetails;

    public Type(Integer id, String name, String image, Product product) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.product = product;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Type otherType)) {
            return false;
        }
        return otherType.id != null && this.id != null ?
                otherType.id.equals(this.id) :otherType.name.equals(this.name);
    }
}
