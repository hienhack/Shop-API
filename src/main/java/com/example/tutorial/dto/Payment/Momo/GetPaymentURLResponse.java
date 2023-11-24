package com.example.tutorial.dto.Payment.Momo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentURLResponse {
    private String partnerCode;
    private String requestId;
    private String orderId;
    private int amount;
    private String responseTime;
    private String message;
    private int resultCode;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
    private String deeplinkWebInApp;
}
