package com.example.tutorial.dto.Order;

import com.example.tutorial.dto.Delivery.DeliveryDTO;
import com.example.tutorial.dto.OrderDetail.OrderDetailDTO;
import com.example.tutorial.dto.Payment.PaymentDTO;
import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.OrderState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDTO {
    private Integer id;
    private OrderState state;
    private DeliveryDTO delivery;
    private boolean isCheckedOut;
    private PaymentDTO payment;
    private List<OrderDetailDTO> orderDetails;
    private int productCost;
    private int totalCost;

    @JsonProperty("created_date")
    private LocalDateTime created;

    @JsonProperty("delivered_date")
    private LocalDateTime delivered;

    @JsonProperty("shipped_date")
    private LocalDateTime shipped;

    @JsonProperty("cancelled_date")
    private LocalDateTime cancelled;

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.state  = entity.getState();
        this.payment = new PaymentDTO(entity.getPayment());
        this.orderDetails = entity.getOrderDetails().stream().map(OrderDetailDTO::new).toList();
        this.productCost = entity.getProductsCost();
        this.totalCost = entity.getTotalCost();
        this.created = entity.getCreated();
        this.delivered = entity.getDelivered();
        this.shipped = entity.getShipped();
        this.cancelled = entity.getCancelled();
        this.isCheckedOut = entity.isCheckedOut();
        this.delivery = new DeliveryDTO(entity.getDelivery());
    }
}
