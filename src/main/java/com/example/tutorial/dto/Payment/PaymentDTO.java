package com.example.tutorial.dto.Payment;

import com.example.tutorial.entity.Payment;
import com.example.tutorial.enumeration.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Integer id;

    @JsonProperty("paid_date")
    private LocalDateTime paid;

    private boolean isPaid;

    @JsonProperty("total_cost")
    private float total;

    @JsonProperty("payment_method")
    private @NotNull PaymentMethod method;

    public PaymentDTO(Payment entity) {
        this.id = entity.getId();
        this.paid = entity.getPaid();
        this.isPaid = entity.getIsPaid();
        this.method = entity.getMethod();
        this.total = entity.getTotal();
    }
}
