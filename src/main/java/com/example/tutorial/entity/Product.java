package com.example.tutorial.entity;

import com.example.tutorial.enumeration.Size;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.validatior.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "thumbnail", length = 100)
    private String thumbnail;

    private Float price;
    private float weight;

    @Column(name = "description", length = 600)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private List<Type> types = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private List<ProductSize> sizes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private List<StockDetail> stock = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Product))
            return false;

        return ((Product)other).id != null ?
                ((Product)other).id.equals(this.id) : ((Product)other).name.equals(this.name);
    }

    public int getInStock(String typeName, Size sizeName) {
        return findStockDetail(typeName, sizeName).getInStock();
    }

    public void setInStock(String typeName, Size sizeName, Integer inStock) {
        findStockDetail(typeName, sizeName).setInStock(inStock);
    }

    private StockDetail findStockDetail(String typeName, Size sizeName) {
        Type type = Type.builder().name(typeName).build();
        ProductSize size = ProductSize.builder().size(sizeName).build();
        if (!this.types.contains(type)) {
            throw new BusinessException("Not found type " + typeName + " in product with id = " + this.id);
        }
        else if (!this.sizes.contains(size)) {
            throw new BusinessException("Not found size " + sizeName + " in product with id = " + this.id);
        }
        int index = stock.indexOf(StockDetail.builder().type(type).size(size).build());
        return stock.get(index);
    }
}