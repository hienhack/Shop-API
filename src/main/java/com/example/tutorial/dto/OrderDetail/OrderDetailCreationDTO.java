package com.example.tutorial.dto.OrderDetail;

import com.example.tutorial.Annotation.EnumType;
import com.example.tutorial.enumeration.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCreationDTO {
    @JsonProperty("product_id")
    private @NotNull Integer productId;

    private @NotEmpty String type;

    @NotNull
    @EnumType(value = Size.class)
    private String size;

    @Min(1)
    private @NotNull Integer quantity;
}
