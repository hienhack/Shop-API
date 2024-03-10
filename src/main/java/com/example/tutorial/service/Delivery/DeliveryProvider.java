package com.example.tutorial.service.Delivery;

import com.example.tutorial.entity.Address;
import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.DeliveryProviderName;

public interface DeliveryProvider {
    int getFee(Address address, float weight);
    void createOrder(Order order);
    DeliveryProviderName providerName();
}
