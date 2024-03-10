package com.example.tutorial.dto.Payment.Momo;

import com.example.tutorial.Annotation.EnumType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPNRequest {
//    private @NotEmpty String partnerCode;
    private @NotEmpty String paymentId;
//    private @NotNull Integer requestId;
//    private @NotNull Integer amount;
//    private @NotEmpty String orderInfo;
//    private @NotEmpty String orderType;
//    private @NotNull Long transld;
//    private @NotNull Integer resultCode;
//    private @NotEmpty String message;
//    private @NotEmpty String payType;
//    private @NotNull Long responseTime;
//    private @NotEmpty String extraData;
//    private @NotEmpty String signature;

    @EnumType(value = IPNRequest.Status.class)
    private @NotEmpty String status;

    public enum Status {
        Success,
        Failed
    }
}
