package com.example.tutorial.event;

import org.springframework.context.ApplicationEvent;

public class PaymentSuccessEvent extends ApplicationEvent {
    private Integer orderId;

    public PaymentSuccessEvent(Integer orderId) {
        super(orderId);
        this.orderId = orderId;
    }
}
