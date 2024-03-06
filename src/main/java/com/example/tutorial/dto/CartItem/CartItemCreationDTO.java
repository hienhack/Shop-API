package com.example.tutorial.dto.CartItem;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CartItemCreationDTO {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer typeId;
    private @NotNull Integer sizeId;
    private @NotNull Integer quantity;
}
