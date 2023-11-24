package com.example.tutorial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private ProductSize size;

    private Integer inStock;

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StockDetail oStockDetail))
            return false;
        return oStockDetail.id != null && this.id != null ? oStockDetail.id.equals(this.id) :
                oStockDetail.type.equals(this.type) && oStockDetail.size.equals(this.size);
    }
}
