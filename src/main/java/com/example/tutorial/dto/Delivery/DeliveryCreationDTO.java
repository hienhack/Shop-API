package com.example.tutorial.dto.Delivery;

import com.example.tutorial.Annotation.EnumType;
import com.example.tutorial.enumeration.DeliveryProviderName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeliveryCreationDTO {
    private @NotNull Integer addressId;
    private @NotEmpty String name;
    private @NotEmpty String phone;

    @NotNull
    @EnumType(value = DeliveryProviderName.class)
    private String serviceName;
}
