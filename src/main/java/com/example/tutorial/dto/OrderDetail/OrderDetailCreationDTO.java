package com.example.tutorial.dto.OrderDetail;

import com.example.tutorial.enumeration.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCreationDTO {
    private Integer productId;
    private String type;
    private Size size;
    private int quantity;
}
