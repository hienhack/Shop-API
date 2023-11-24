package com.example.tutorial.entity;

import com.example.tutorial.enumeration.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "product_size")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "size", length = 4)
    @Enumerated(EnumType.STRING)
    private Size size;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "size")
    private List<SizeDescription> sizeDescriptions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductSize(Integer id, Size size, Map<String, String> sizeDescriptions, Product product) {
        this.id = id;
        this.size = size;
        this.product = product;

        for (String key : sizeDescriptions.keySet()) {
            this.sizeDescriptions.add(new SizeDescription(null, key, sizeDescriptions.get(key), this));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ProductSize otherSize))
            return false;

        return otherSize.id != null && id != null ?
                otherSize.id.equals(this.id) : otherSize.size.equals(this.size);
    }
}
