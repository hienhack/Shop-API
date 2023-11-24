package com.example.tutorial.service;

import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    public int getFee(Integer addressId, int weight) {
        return 30000;
    }

    public void createOrder(Integer orderId) {

    }

//    public void updateDeliveryState(String state)
}
