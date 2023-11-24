package com.example.tutorial.dto.Payment.Momo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPNRequest {
    private String partnerCode;
    private String orderld;
    private Integer requestId;
    private int amount;
    private String orderInfo;
    private String orderType;
    private long transld;
    private int resultCode;
    private String message;
    private String payType;
    private long responseTime;
    private String extraData;
    private String signature;
}
