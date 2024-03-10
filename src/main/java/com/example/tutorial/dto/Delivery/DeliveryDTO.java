package com.example.tutorial.dto.Delivery;

import com.example.tutorial.entity.Delivery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryDTO {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private float weight;
    private Integer fee;
    @JsonProperty("service_name")
    private String serviceName;

    public DeliveryDTO(Delivery entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.address = entity.getAddress();
        this.weight = entity.getWeight();
        this.fee = entity.getFee();
        this.serviceName = entity.getServiceName().name();
    }
}
