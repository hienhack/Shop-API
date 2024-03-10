package com.example.tutorial.dto.Order;

import com.example.tutorial.Annotation.EnumType;
import com.example.tutorial.dto.Delivery.DeliveryCreationDTO;
import com.example.tutorial.dto.OrderDetail.OrderDetailCreationDTO;
import com.example.tutorial.enumeration.PaymentMethod;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderCreationDTO {
    @NotNull
    @EnumType(value = PaymentMethod.class)
    private String paymentMethod;

    @NotNull
    @Valid
    private DeliveryCreationDTO delivery;

    @NotNull
    @Valid
    private List<OrderDetailCreationDTO> orderDetails;
}
