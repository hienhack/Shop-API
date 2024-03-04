package com.example.tutorial.dto.StockDetail;

import com.example.tutorial.entity.StockDetail;
import com.example.tutorial.enumeration.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonIgnoreProperties(value = "productId", allowSetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailDTO {
    private Integer id;

    @NotEmpty
    private String type;

    @NotNull
    private Size size;

    @NotNull
    private Integer inStock;

    public StockDetailDTO(StockDetail entity) {
        this.id = entity.getId();
        this.type = entity.getType().getName();
        this.size = entity.getSize().getSize();
        this.inStock = entity.getInStock();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StockDetailDTO)) {
            return false;
        }

        return ((StockDetailDTO) other).type.equals(this.type) &&
                ((StockDetailDTO) other).size.equals(this.size);
    }
}
