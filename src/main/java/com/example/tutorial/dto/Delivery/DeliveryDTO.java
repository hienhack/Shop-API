package com.example.tutorial.dto.Delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeliveryDTO {
    private Integer addressId;

    @JsonProperty("receiver_name")
    private String name;

    @JsonProperty("receiver_phone")
    private String phone;
}
