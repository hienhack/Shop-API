package com.example.tutorial.dto.Order;

import com.example.tutorial.dto.OrderDetail.OrderDetailDTO;
import com.example.tutorial.dto.Payment.PaymentDTO;
import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.OrderState;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Integer id;
    private OrderState state;
    private String deliveryService;
    private String address;
    private PaymentDTO payment;
    private List<OrderDetailDTO> orderDetails;
    private float productCost;
    private float deliveryCost;
    private float totalCost;

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
        this.address = entity.getDelivery().getAddress();
        this.payment = new PaymentDTO(entity.getPayment());
        this.orderDetails = entity.getOrderDetails().stream().map(OrderDetailDTO::new).toList();
        this.productCost = entity.getProductsCost();
        this.deliveryCost = entity.getDelivery().getFee();
        this.totalCost = entity.getTotalCost();
        this.created = entity.getCreated();
        this.delivered = entity.getDelivered();
        this.shipped = entity.getShipped();
        this.cancelled = entity.getCancelled();
    }
}
