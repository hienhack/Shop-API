package com.example.tutorial.dto.Payment.Momo;

import lombok.Data;

@Data
public class IPNResponse {
    private String partnerCode;
    private String requestId;
    private String orderld;
    private int resultCode;
    private String message;
    private long responseTime;
    private String extraData;
    private String signature;
}
