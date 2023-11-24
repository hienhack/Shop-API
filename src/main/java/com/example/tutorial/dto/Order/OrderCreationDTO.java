package com.example.tutorial.dto.Order;

import com.example.tutorial.dto.Delivery.DeliveryDTO;
import com.example.tutorial.dto.OrderDetail.OrderDetailCreationDTO;
import com.example.tutorial.dto.Payment.PaymentDTO;
import com.example.tutorial.enumeration.OrderState;
import com.example.tutorial.enumeration.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationDTO {
    private Integer id;
    private OrderState state;
    private DeliveryDTO delivery;
    private List<OrderDetailCreationDTO> orderDetails;
    private PaymentDTO payment;
}
