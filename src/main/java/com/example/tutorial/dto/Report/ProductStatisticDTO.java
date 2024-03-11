package com.example.tutorial.dto.Report;

import com.example.tutorial.dto.Product.ProductInListDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStatisticDTO {
    private ProductInListDTO product;
    @JsonProperty("total_sold")
    private int totalSold;
}
